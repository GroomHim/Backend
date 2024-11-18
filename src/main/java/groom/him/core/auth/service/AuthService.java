package groom.him.core.auth.service;

import groom.him.common.models.constant.Role;
import groom.him.core.auth.dto.request.SignUpRequest;
import groom.him.core.auth.dto.response.SignInResponse;
import groom.him.core.auth.util.JwtTokenProvider;
import groom.him.core.model.member.exception.MemberErrorCode;
import groom.him.core.model.member.exception.MemberException;
import groom.him.core.model.member.repository.MemberRepository;
import groom.him.domain.member.models.constant.Provider;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.models.entity.data.Password;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public SignInResponse signIn(final String loginId, final String password) throws Exception {
        MemberEntity member = memberRepository.findByLoginIdAndIsCancelFalse(loginId).orElseThrow(() -> new MemberException.MemberNotExistException(MemberErrorCode.MEMBER_NOT_EXIST));
        String pwd = member.getPassword();
        String salt = member.getSalt();

        if (!pwd.equals(hashing(password, salt)))
            throw new MemberException.MemberNotExistException(MemberErrorCode.MEMBER_NOT_EXIST);
        String accessToken = jwtTokenProvider.createToken(member.getMemberId(), toAuthentication(member.getMemberId(), member.getRole()), member.getCi());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberId(), toAuthentication(member.getMemberId(), member.getRole()), member.getCi());
        member.changeRefreshToken(refreshToken);

        return new SignInResponse(accessToken, refreshToken);
    }

    private String getSalt() throws Exception {
        SecureRandom rnd = new SecureRandom();
        byte[] temp = new byte[SALT_SIZE];
        rnd.nextBytes(temp);

        return byteToString(temp);
    }

    private String hashing(String password, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] passwordBtye;

        for (int i = 0; i < 10000; i++) {
            String temp = password + salt;
            md.update(temp.getBytes());
            passwordBtye = md.digest();
            password = byteToString(passwordBtye);
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

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findByMemberIdAndIsCancelFalse(Integer.parseInt(memberId)).orElseThrow(() -> new UsernameNotFoundException(memberId));
    }

    public Boolean existsRefreshToken(Integer userId, String refreshToken) {
        Optional<MemberEntity> optionalMember = memberRepository.findByMemberIdAndRefreshToken(userId, refreshToken);
        return !optionalMember.orElseThrow(() -> new MemberException.MemberNotExistException(MemberErrorCode.MEMBER_NOT_EXIST)).getRefreshToken().isEmpty();
    }

    public MemberEntity signUp(SignUpRequest request) throws Exception {
        String salt = getSalt();
        if (isCiExist(request.ci())) throw new MemberException.MemberDuplicatedException(MemberErrorCode.MEMBER_DUPLICATED);
        MemberEntity member = MemberEntity.builder()
                .loginId(request.loginId())
                .ci(request.ci())
                .birth(request.birth())
                .password(new Password(hashing(request.password(), salt), salt))
                .name(request.name())
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
        optionalMember.orElseThrow(() -> new MemberException.MemberNotExistException(MemberErrorCode.MEMBER_NOT_EXIST)).changeRefreshToken(null);
    }

    private Boolean isCiExist(String ci){
        Optional<MemberEntity> optionalMember = memberRepository.findByCiAndIsCancelFalse(ci);
        return optionalMember.isPresent();
    }

    public boolean validateLoginId(String loginId){
        Optional<MemberEntity> member = memberRepository.findByLoginIdAndIsCancelFalse(loginId);
        if (member.isPresent()) throw new MemberException.MemberDuplicatedException(MemberErrorCode.MEMBER_DUPLICATED);
        String regex = "[/\\[\\]{}?.,;:|\\)*~`!^\\-_+<>@#$%&\\=('\"]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(loginId);
        return !matcher.find();
    }
}