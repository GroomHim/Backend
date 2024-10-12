package groom.him.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    TODO: jwt token provider, auth service 작성
//    private final JwtTokenProvider jwtTokenProvider;
//    private final AuthService authService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //rest api
        http.httpBasic().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();

        // TODO: API명세서 작성 후, authenticated path 인가 필요
        http.authorizeRequests()
                .antMatchers("/swagger-ui.html", "/swagger-ui/**","/swagger-resources/**","/v2/api-docs", "/swagger/**", "/webjars/**", "/configuration/ui", "/configuration/security").anonymous()
                .mvcMatchers(HttpMethod.GET, "/groomhim/v1/health-check").permitAll()
                .mvcMatchers(HttpMethod.POST, "/groomhim/auth/logout").permitAll()
                .anyRequest().authenticated();

        // authenticated exception 권한예외처리
        http.exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        // response.sendRedirect("/admin/auth/denied"); // TODO: 접근권한이없음을 알리는 페이지 경로로 리다이렉트
                        throw new java.nio.file.AccessDeniedException("접근 권한이 없습니다.");
                    }
                });


        // TODO: jwt auth filter inject
        // http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider,authService), UsernamePasswordAuthenticationFiltet.class);
    }

    // jwtAuthenticationFilter 필터 안 타도록 설정해주기
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/groomhim/auth/sign-in")
                .antMatchers("/groomhim/auth/refresh-token")
                .antMatchers("/groomhim/v1/health-check")
                .antMatchers("/swagger-ui.html/**", "/swagger-ui/**","/swagger-resources/**","/v2/api-docs", "/swagger/**", "/webjars/**", "/configuration/ui", "/configuration/security");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
