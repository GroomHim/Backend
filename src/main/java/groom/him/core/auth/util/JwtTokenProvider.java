package groom.him.core.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-valid-time}")
    private long tokenValidTime;

    @Value("${jwt.refreshToken-valid-time}")
    private long refreshTokenValidTime;

    private final String TOKEN_HEADER_NAME = "Authorization";

    private final String REFRESHTOKEN_HEADER_NAME = "REFRESH-TOKEN";

    private final String AUTHORITIES_KEY = "role";

    public String createToken(Integer memberId, Authentication authentication, String ci) {
        return generateToken(memberId, authentication, tokenValidTime, ci);
    }

    public String createRefreshToken(Integer memberId, Authentication authentication, String ci) {
        return generateToken(memberId, authentication, refreshTokenValidTime, ci);
    }

    public String generateToken(Integer memberId, Authentication authentication, long expireTime, String ci) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();

        return Jwts.builder()
                .setSubject(memberId.toString())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(now.getTime() + expireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Collection<? extends GrantedAuthority> getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return authorities;
    }

    public String resolveToken(HttpServletRequest request) {
        if (request.getHeader(TOKEN_HEADER_NAME) == null) {
            return null; // TODO: Exception
        }
        String authorization = request.getHeader(TOKEN_HEADER_NAME);
        if (Pattern.matches("^Bearer .*", authorization)) {
            authorization = authorization.replaceAll("^Bearer( )*", "");
            return authorization;
        } else throw new RuntimeException("Invalid token");
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        if (request.getHeader(REFRESHTOKEN_HEADER_NAME) == null) {
            return null;
        }
        return request.getHeader(REFRESHTOKEN_HEADER_NAME);
    }

    public boolean isTokenNonExpired(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            log.info("expired jwt exception");
            return false;
        } catch (UnsupportedJwtException unsupportedJwtException) {
            log.info("unsupported jwt exception");
            return false;
        } catch (MalformedJwtException malformedJwtException) {
            log.info("malformed jwt exception");
            return false;
        } catch (SignatureException signatureException) {
            log.info("signature exception");
            return false;
        } catch (IllegalArgumentException illegalArgumentException) {
            log.info("illegal argument exception");
            return false;
        } catch (Exception e) {
            log.info("unknown exception");
            return false;
        }
    }
}