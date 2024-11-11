package groom.him.core.auth.service;

import groom.him.core.auth.util.JwtTokenProvider;
import groom.him.core.dto.Response;
import groom.him.core.model.user.entity.Member;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
    private final UserRepository userRepository;
    //    private final EmailSender emailSender;
    private final JwtTokenProvider jwtTokenProvider;
//    private final TextMessageProvider textMessageProvider;
//    private final AuthenticationManager authenticationManager;

//    private void validateNameAndPassword(String name,String password){
//        String namePattern = "^[ㄱ-ㅎ|가-힣]+$";//한글만 가능
//        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~]{8,}$";//총8자 이상, 영문자, 숫자, 특수문자 각각 하나이상
//        if (!Pattern.matches(namePattern, name)){ throw new InvalidNameException(); }
//        if (!Pattern.matches(passwordPattern, password)){ throw new InvalidPasswordException(); }
//    }

    private Authentication toAuthentication(Long userId, Member.Role role) {
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(role.toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new org.springframework.security.core.userdetails.User(userId.toString(), "groomhim", authorities);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, "groomhim", authorities);
        return authenticationToken;
    }


    // TODO : 이부분이 나이스인증부분
    @Transactional
    public Response verifyingPhoneNumber(
            @NotNull final String phoneNumber
    ) {
        Optional<User> userEntityOptional = userRepository.findByPhoneNumberAndIsEnabledTrue(phoneNumber);

        if (userEntityOptional.isPresent()) {
            final User user = userEntityOptional.get();

            // 해당 핸드폰 번호가 인증 가능한 시간인지 확인
            final Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            if (currentTime.before(user.getPhoneNumberVerifierValidAt())) {
                final String message = String.format(
                        "현재 핸드폰 번호를 인증할 수 없습니다. %d초 뒤 재 인증이 가능합니다. %d",
                        (user.getPhoneNumberVerifierValidAt().getTime() - currentTime.getTime()) / 1000,
                        System.currentTimeMillis()
                );
                System.out.println(message);
                return RestFailResponse.newInstance(
                        HttpStatus.FORBIDDEN,
                        message
                );
            }

            // 인증 가능한 부분 갱신
            final Timestamp expiredAt = new Timestamp(System.currentTimeMillis() + +180000);
            final Integer verificationCode = ThreadLocalRandom.current().nextInt(100000, 999999);
            user.setPhoneNumberVerifierValidAt(new Timestamp(System.currentTimeMillis() + +60000));
            user.setLoginVerificationExpiredAt(expiredAt);
            user.setLoginVerificationCode(verificationCode);

            textMessageProvider.sendOne(
                    phoneNumber,
                    String.format("인증번호는 %d 입니다.", verificationCode)
            );

            return RestSuccessResponse.newInstance(
                    VerificationResponse.newInstance("발급된 인증번호를 입력해주세요.", expiredAt)
            );
        } else {
            return RestFailResponse.newInstance(
                    HttpStatus.NOT_FOUND,
                    "해당 핸드폰 번호를 사용하는 계정을 찾을 수 없습니다."
            );
        }


    }

    public SignInResponse signIn(
            final String phoneNumber,
            final Integer verificationCode
    ) {
        Optional<User> optionalUser = userRepository.findByPhoneNumberAndLoginVerificationCodeAndLoginVerificationExpiredAtIsAfterAndIsEnabledTrue(
                phoneNumber,
                verificationCode,
                new Timestamp(System.currentTimeMillis())
        );
        User user = optionalUser.orElseThrow(UserNotExistException::new);

        String accessToken = jwtTokenProvider.createToken(user.getId(), toAuthentication(user.getId(), user.getRole()));
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(), toAuthentication(user.getId(), user.getRole()));
        user.setRefreshToken(refreshToken);
        return new SignInResponse(accessToken, refreshToken);
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByIdAndIsEnabledTrue(Long.valueOf(userId))
                .orElseThrow(() -> new UsernameNotFoundException(userId));
    }

//    public User loadUserByNickname(String nickname, Long userId){
//        return userRepository.findByNicknameAndId(nickname, userId).orElseThrow(() -> new UsernameNotFoundException(nickname));
//    }

    public RefreshTokenResponse regenerateToken(User user) {
        final String accessToken = jwtTokenProvider.createToken(user.getId(), toAuthentication(user.getId(), user.getRole()));
        final String refreshToken = jwtTokenProvider.createRefreshToken(user.getId(), toAuthentication(user.getId(), user.getRole()));
        Optional<User> optionalUser = userRepository.findById(user.getId());
        optionalUser.orElseThrow(UserNotExistException::new).setRefreshToken(refreshToken);
        return new RefreshTokenResponse(accessToken, refreshToken);
//        redisService.setValues(user.getNickname(), refreshToken);
    }

    public Boolean existsRefreshToken(Long userId, String refreshToken) {
        Optional<User> optionalUser = userRepository.findByIdAndRefreshToken(userId, refreshToken);
        return !optionalUser.orElseThrow(UserNotExistException::new).getRefreshToken().isEmpty();
    }

    public String logout(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        optionalUser.orElseThrow(UserNotExistException::new).setRefreshToken(null);
        return "로그아웃에 성공하였습니다.";
    }
}