package groom.him.core.auth.service;

import groom.him.core.auth.dto.RefreshTokenResponse;
import groom.him.core.auth.dto.SignInResponse;
import groom.him.core.auth.util.JwtTokenProvider;
import groom.him.core.model.user.entity.Member;
import groom.him.core.model.user.exception.MemberErrorCode;
import groom.him.core.model.user.exception.MemberException;
import groom.him.core.model.user.repository.MemberRepository;
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

import java.sql.Timestamp;
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

//    private void validateNameAndPassword(String name,String password){
//        String namePattern = "^[ㄱ-ㅎ|가-힣]+$";//한글만 가능
//        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~]{8,}$";//총8자 이상, 영문자, 숫자, 특수문자 각각 하나이상
//        if (!Pattern.matches(namePattern, name)){ throw new InvalidNameException(); }
//        if (!Pattern.matches(passwordPattern, password)){ throw new InvalidPasswordException(); }
//    }

    private Authentication toAuthentication(Integer userId, Member.Role role) {
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(role.toString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        UserDetails principal = new org.springframework.security.core.userdetails.User(userId.toString(), "groomhim", authorities);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, "groomhim", authorities);
        return authenticationToken;
    }


    public SignInResponse signIn(final String phoneNumber, final Integer verificationCode) {
        Optional<Member> optionalUser = memberRepository.findByPhoneNumberAndLoginVerificationCodeAndLoginVerificationExpiredAtIsAfterAndIsEnabledTrue(phoneNumber, verificationCode, new Timestamp(System.currentTimeMillis()));
        Member user = optionalUser.orElseThrow();

        String accessToken = jwtTokenProvider.createToken(user.getMemberId(), toAuthentication(user.getMemberId(), user.getRole()), user.getCi());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getMemberId(), toAuthentication(user.getMemberId(), user.getRole()), user.getCi());
        user.setRefreshToken(refreshToken);
        return new SignInResponse(accessToken, refreshToken);
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return memberRepository.findByIdAndIsEnabledTrue(Integer.parseInt(userId)).orElseThrow(() -> new UsernameNotFoundException(userId));
    }

    public RefreshTokenResponse regenerateToken(Member user) {
        final String accessToken = jwtTokenProvider.createToken(user.getMemberId(), toAuthentication(user.getMemberId(), user.getRole()), user.getCi());
        final String refreshToken = jwtTokenProvider.createRefreshToken(user.getMemberId(), toAuthentication(user.getMemberId(), user.getRole()), user.getCi());
        Optional<Member> optionalUser = memberRepository.findById(user.getMemberId());
        optionalUser.orElseThrow().setRefreshToken(refreshToken);
        return new RefreshTokenResponse(accessToken, refreshToken);
//        redisService.setValues(user.getNickname(), refreshToken);
    }

    public Boolean existsRefreshToken(Integer userId, String refreshToken) {
        Optional<Member> optionalUser = memberRepository.findByIdAndRefreshToken(userId, refreshToken);
        return !optionalUser.orElseThrow().getRefreshToken().isEmpty();
    }

    public String logout(Member user) {
        Optional<Member> optionalUser = memberRepository.findById(user.getMemberId());
        optionalUser.orElseThrow().setRefreshToken(null);
        return "로그아웃에 성공하였습니다.";
    }


    //회원가입-auth, 중복화인3개-auth, 로그인로그아웃-auth, 탈퇴하기-user
}