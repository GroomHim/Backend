package groom.him.core.auth.util.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import groom.him.core.auth.service.AuthService;
import groom.him.core.auth.util.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.InvalidParameterException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshValidateInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String refreshToken = jwtTokenProvider.resolveToken(request);
        try {
            if (refreshToken != null) {
                Integer userId = Integer.parseInt(jwtTokenProvider.getUserId(refreshToken));
                if (authService.existsRefreshToken(userId, refreshToken) && jwtTokenProvider.isTokenNonExpired(refreshToken)) {
                    try {
                        Authentication authentication = getAuthentication(refreshToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } catch (UsernameNotFoundException e) {
                        log.info("username not found exception" + e.getMessage());
                        return false;
                    }
                } else {
                    log.info("refresh token doesn't exist in database");
                    return false;
                }
            } else {
                log.info("refresh token is null");
                return false;
            }
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            e.printStackTrace();
            log.info("invalid token");
            throw new InvalidParameterException("유효하지 않은 토큰입니다.");
        }
        return true;
    }


    private Authentication getAuthentication(String token) throws UsernameNotFoundException, JsonProcessingException {
        UserDetails userDetails = authService.loadUserByUsername(jwtTokenProvider.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}