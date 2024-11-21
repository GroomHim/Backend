package groom.him.core.auth.util.filter;

import groom.him.core.auth.service.AuthService;
import groom.him.core.auth.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.AuthenticationException;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null && jwtTokenProvider.isTokenNonExpired(token)) {
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (token == null) {
            throw new RuntimeException("token doesn't exist!");
        } else if (!jwtTokenProvider.isTokenNonExpired(token)) {
            throw new RuntimeException("invalid token!");
        }
        filterChain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token) throws UsernameNotFoundException {
        UserDetails userDetails = authService.loadUserByUsername(jwtTokenProvider.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", jwtTokenProvider.getAuthentication(token));
    }
}