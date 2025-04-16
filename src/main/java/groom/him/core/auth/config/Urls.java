package groom.him.core.auth.config;

public class Urls {

    public static final String[] PERMIT_URLS = new String[]{
        "/webjars/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html/**",
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/v3/api-docs/**",
        "/swagger/**",
        "/v1/health-check",
        "/v1/auth/sign-up",
        "/v1/auth/social/sign-up",
        "/v1/auth/sign-in",
        "/v1/auth/social/sign-in",
        "/v1/auth/validate/login-id/{loginId}",
        "/v1/auth/validate/nickname/{nickname}"
    };
}
