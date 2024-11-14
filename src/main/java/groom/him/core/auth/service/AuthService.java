package groom.him.core.auth.service;

import groom.him.common.models.constant.Role;
import groom.him.core.auth.dto.request.SignUpRequest;
import groom.him.core.auth.dto.response.RefreshTokenResponse;
import groom.him.core.auth.dto.response.LogInResponse;
import groom.him.core.auth.util.JwtTokenProvider;
import groom.him.core.model.member.exception.MemberErrorCode;
import groom.him.core.model.member.exception.MemberException;
import groom.him.core.model.member.repository.MemberRepository;
import groom.him.domain.member.models.constant.Provider;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.models.entity.data.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public LogInResponse logIn(final String loginId, final String password) {
//        Optional<MemberEntity> optionalUser = memberRepository.findByPhoneNumberAndLoginVerificationCodeAndLoginVerificationExpiredAtIsAfterAndIsEnabledTrue(phoneNumber, verificationCode, new Timestamp(System.currentTimeMillis()));
        MemberEntity member = memberRepository.findByLoginIdAndIsCancelTrue(loginId).orElseThrow(() -> new MemberException.MemberNotExistException(MemberErrorCode.MEMBER_NOT_EXIST));
        String accessToken = jwtTokenProvider.createToken(member.getMemberId(), toAuthentication(member.getMemberId(), member.getRole()), member.getCi());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberId(), toAuthentication(member.getMemberId(), member.getRole()), member.getCi());
        member.changeRefreshToken(refreshToken);
        return new LogInResponse(accessToken, refreshToken);
    }

    // SALT 값 생성
    private String getSalt() throws Exception {
        SecureRandom rnd = new SecureRandom();
        byte[] temp = new byte[SALT_SIZE];
        rnd.nextBytes(temp);

        return byteToString(temp);
    }

    // 비밀번호 해싱
    private String hashing(byte[] password, String Salt) throws Exception {

        MessageDigest md = MessageDigest.getInstance("SHA-256");    // SHA-256 해시함수를 사용

        // key-stretching
        for (int i = 0; i < 10000; i++) {
            String temp = password + Salt;    // 패스워드와 Salt 를 합쳐 새로운 문자열 생성
            md.update(temp.getBytes());                        // temp 의 문자열을 해싱하여 md 에 저장해둔다
            password = md.digest();                            // md 객체의 다이제스트를 얻어 password 를 갱신한다
        }

        return byteToString(password);
    }

    // 바이트 값을 16진수로 변경해준다
    private String byteToString(byte[] temp) {
        StringBuilder sb = new StringBuilder();
        for (byte a : temp) {
            sb.append(String.format("%02x", a));
        }
        return sb.toString();
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findByMemberIdAndIsCancelTrue(Integer.parseInt(memberId)).orElseThrow(() -> new UsernameNotFoundException(memberId));
    }

    public RefreshTokenResponse regenerateToken(MemberEntity user) {
        final String accessToken = jwtTokenProvider.createToken(user.getMemberId(), toAuthentication(user.getMemberId(), user.getRole()), user.getCi());
        final String refreshToken = jwtTokenProvider.createRefreshToken(user.getMemberId(), toAuthentication(user.getMemberId(), user.getRole()), user.getCi());
        Optional<MemberEntity> optionalMember = memberRepository.findById(user.getMemberId());
        optionalMember.orElseThrow(() -> new MemberException.MemberNotExistException(MemberErrorCode.MEMBER_NOT_EXIST)).changeRefreshToken(refreshToken);
        return new RefreshTokenResponse(accessToken, refreshToken);
    }

    public Boolean existsRefreshToken(Integer userId, String refreshToken) {
        Optional<MemberEntity> optionalMember = memberRepository.findByMemberIdAndRefreshToken(userId, refreshToken);
        return !optionalMember.orElseThrow(() -> new MemberException.MemberNotExistException(MemberErrorCode.MEMBER_NOT_EXIST)).getRefreshToken().isEmpty();
    }

    public MemberEntity signUp(SignUpRequest request) throws Exception {
        String salt = getSalt();
        MemberEntity member = MemberEntity.builder()
                .loginId(request.loginId())
                .ci(request.ci())
                .birth(request.birth())
                .password(new Password(hashing(request.password().getBytes(), salt), salt))
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

    public String logout(MemberEntity member) {
        Optional<MemberEntity> optionalMember = memberRepository.findById(member.getMemberId());
        optionalMember.orElseThrow(() -> new MemberException.MemberNotExistException(MemberErrorCode.MEMBER_NOT_EXIST)).changeRefreshToken(null);
        return "로그아웃에 성공하였습니다.";
    }
}