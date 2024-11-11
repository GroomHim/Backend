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
    private static final String AUTHORITIES_KEY = "role";
    private static final String CI_HEADER_NAME = "CI-TOKEN";

    public String createToken(Long userId, Authentication authentication, String ci) {
        return generateToken(userId, authentication, tokenValidTime, ci);
    }

    public String createRefreshToken(Long userId, Authentication authentication, String ci){
        return generateToken(userId, authentication, refreshTokenValidTime, ci);
    }

    public String generateToken(Long userId, Authentication authentication, long expireTime, String ci){
//        System.out.println(authentication.getCredentials());
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim(AUTHORITIES_KEY,authorities)
                .claim(CI_HEADER_NAME, ci)
                .setExpiration(new Date(now.getTime() + expireTime))//유닉스타임으로 변경
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }


    public String getUserId(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getCI(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(CI_HEADER_NAME).toString();
    }


//    public String getNickname(String token){
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("nickname").toString();
//    }

    //토큰 안에 있는 authorities를 가져온 뒤, "new UserDetails(userId, authorities)" 객체를 만들고, 이 두개로 UsernamepasswordAuthenticationToken(Authentication객체)을 만듦.
    public Collection<? extends GrantedAuthority> getAuthentication(String token){
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        if(claims.get(AUTHORITIES_KEY) == null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
//        if(claims.get(CI_HEADER_NAME)){
//          TODO : 해당 ci값의 유저와 과 유저아이디의 전화번호 or 이름이 동일한 지 비교
//        }
        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return authorities;
        // UserDetails 객체를 만들어서 Authentication 리턴
    }

    //파싱하는 부분
    public String resolveToken(HttpServletRequest request) {
        if (request.getHeader(TOKEN_HEADER_NAME) == null) {
            return null; // TODO: Exception
        }
        String authorization = request.getHeader(TOKEN_HEADER_NAME);
        if(Pattern.matches("^Bearer .*", authorization)){
            authorization = authorization.replaceAll("^Bearer( )*", "");
            return authorization;
        } else throw new RuntimeException("Invalid token");
    }

//    public String resolveRefreshToken(HttpServletRequest request){
//        if(request.getHeader(REFRESHTOKEN_HEADER_NAME) == null){
//            return null;
//        }
//        return request.getHeader(REFRESHTOKEN_HEADER_NAME);
//    }

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
