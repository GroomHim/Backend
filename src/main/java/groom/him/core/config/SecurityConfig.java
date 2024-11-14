package groom.him.core.config;

import groom.him.core.auth.service.AuthService;
import groom.him.core.auth.util.JwtTokenProvider;
import groom.him.core.auth.util.filter.JwtAuthenticationFilter;
import groom.him.core.auth.util.handler.AuthAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.Collections;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
//    TODO: jwt token provider, auth service 작성
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;
    private final String ORIGIN = "http://localhost:3000";
    private final String[] FRONT_SRC_URLS = new String[]{"/webjars/**", "/configuration/ui", "/configuration/security"};
    private final String[] DOCS_SRC_URLS = new String[]{"/swagger-ui.html/**", "/swagger-ui/**", "/swagger-resources/**", "/v1/api-docs", "/swagger/**","/groomhim/v1/health-check"};
    private final String[] AUTH_URLS = new String[] {"/api/v1/auth/sign-up", "/groomhim/auth/refresh-token"};
    private final AuthAccessDeniedHandler accessDeniedCustomHandler = AuthAccessDeniedHandler.getInstance();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsConfigurer-> corsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().permitAll() // TODO : 운영 서버에서는 authenticated()로 변경 요함
                )
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)).accessDeniedHandler(accessDeniedCustomHandler));

        // TODO: Add JWT authentication filter
         http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, authService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(FRONT_SRC_URLS)
                .requestMatchers(DOCS_SRC_URLS)
                .requestMatchers(AUTH_URLS);
    }

    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList(ORIGIN));
            config.setAllowCredentials(true);
            return config;
        };
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
