package groom.him.core.auth.service;

import groom.him.common.models.constant.Role;
import groom.him.core.auth.dto.request.SignUpRequest;
import groom.him.core.auth.dto.response.RefreshTokenResponse;
import groom.him.core.auth.dto.response.SignInResponse;
import groom.him.core.auth.util.CookieUtils;
import groom.him.core.auth.util.JwtTokenProvider;
import groom.him.core.exception.CommonErrorCode;
import groom.him.core.exception.CommonException;
import groom.him.core.model.member.exception.MemberErrorCode;
import groom.him.core.model.member.exception.MemberException;
import groom.him.core.model.member.repository.MemberRepository;
import groom.him.domain.member.models.constant.Provider;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.models.entity.data.Password;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private static final int SALT_SIZE = 16;

    private Authentication toAuthentication(Integer memberId, Role role) {
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(role.toString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        UserDetails principal = new org.springframework.security.core.userdetails.User(memberId.toString(), "groomhim", authorities);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, "groomhim", authorities);
        return authenticationToken;
    }

    public SignInResponse signIn(final String loginId, final String password, HttpServletResponse httpRes) throws Exception {
        MemberEntity member = memberRepository.findByLoginIdAndIsCancelFalse(loginId).orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXIST));
        String pwd = member.getPassword();
        String salt = member.getSalt();

        if (!pwd.equals(hashing(password, salt)))
            throw new MemberException(MemberErrorCode.MEMBER_NOT_EXIST);
        String accessToken = jwtTokenProvider.createToken(member.getMemberId(), toAuthentication(member.getMemberId(), member.getRole()));
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberId(), toAuthentication(member.getMemberId(), member.getRole()));
        CookieUtils.addRememberMeCookie(httpRes, URLEncoder.encode("Bearer " + accessToken, StandardCharsets.UTF_8));

        member.changeRefreshToken(refreshToken);
        return new SignInResponse(accessToken, refreshToken);
    }

    public RefreshTokenResponse regenerateToken(MemberEntity member, HttpServletResponse httpRes){
        final String accessToken = jwtTokenProvider.createToken(member.getMemberId(), toAuthentication(member.getMemberId(), member.getRole()));
        final String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberId(), toAuthentication(member.getMemberId(), member.getRole()));
        Optional<MemberEntity> optionalUser = memberRepository.findById(member.getMemberId());
        optionalUser.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXIST)).changeRefreshToken(refreshToken);
        CookieUtils.addRememberMeCookie(httpRes, URLEncoder.encode("Bearer " + accessToken, StandardCharsets.UTF_8));
        return new RefreshTokenResponse(accessToken, refreshToken);
    }

    public String getSalt() {
        SecureRandom rnd = new SecureRandom();
        byte[] temp = new byte[SALT_SIZE];
        rnd.nextBytes(temp);

        return byteToString(temp);
    }

    public String hashing(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] passwordBtye;

            for (int i = 0; i < 10000; i++) {
                String temp = password + salt;
                md.update(temp.getBytes());
                passwordBtye = md.digest();
                password = byteToString(passwordBtye);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new CommonException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
        return password;
    }

    private String byteToString(byte[] temp) {
        StringBuilder sb = new StringBuilder();
        for (byte a : temp) {
            sb.append(String.format("%02x", a));
        }
        return sb.toString();
    }

    public Password encryptPassword(String password) {
        String salt = getSalt();
        return new Password(hashing(password, salt), salt);
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findByMemberIdAndIsCancelFalse(Integer.parseInt(memberId)).orElseThrow(() -> new UsernameNotFoundException(memberId));
    }

    public Boolean existsRefreshToken(Integer userId, String refreshToken) {
        Optional<MemberEntity> optionalMember = memberRepository.findByMemberIdAndRefreshToken(userId, refreshToken);
        return !optionalMember.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXIST)).getRefreshToken().isEmpty();
    }

    public MemberEntity signUp(SignUpRequest request) throws Exception {
        String salt = getSalt();
        if (isCiExist(request.ci())) throw new MemberException(MemberErrorCode.MEMBER_DUPLICATED);
        MemberEntity member = MemberEntity.builder()
                .loginId(request.loginId())
                .ci(request.ci())
                .birth(request.birth())
                .password(new Password(hashing(request.password(), salt), salt))
                .name(request.name())
                .email(request.email())
                .gender(request.gender())
                .isCancel(false)
                .role(Role.USER)
                .nickname(request.nickname())
                .phoneNumber(request.phoneNumber())
                .provider(Provider.GROOMHIM)
                .build();

        memberRepository.save(member);
        return member;
    }

    public void signOut(MemberEntity member) {
        Optional<MemberEntity> optionalMember = memberRepository.findById(member.getMemberId());
        optionalMember.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXIST)).changeRefreshToken(null);
    }

    private Boolean isCiExist(String ci){
        Optional<MemberEntity> optionalMember = memberRepository.findByCiAndIsCancelFalse(ci);
        return optionalMember.isPresent();
    }

    public boolean validateLoginId(String loginId){
        Optional<MemberEntity> member = memberRepository.findByLoginId(loginId);
        if (member.isPresent()) throw new MemberException(MemberErrorCode.MEMBER_DUPLICATED);
        String regex = "[/\\[\\]{}?.,;:|\\)*~`!^\\-_+<>@#$%&\\=('\"]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(loginId);
        return !matcher.find();
    }

    public boolean validateNickname(String nickname){
        Optional<MemberEntity> member = memberRepository.findByNickname(nickname);
        if (member.isPresent()) throw new MemberException(MemberErrorCode.MEMBER_DUPLICATED);
        return true;
    }

    public String findLoginIdByCi(String ci) {
        MemberEntity member = memberRepository.findByCi(ci).orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXIST));
        return member.getLoginId();
    }
}