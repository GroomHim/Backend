package groom.him.core.auth.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.token-valid-time}")
    private long tokenValidTime;
    @Value("${jwt.refreshToken-valid-time}")
    private long refreshTokenValidTime;

}
