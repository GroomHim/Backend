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
        "/v1/auth/signUp",
        "/v1/auth/social/signUp",
        "/v1/auth/signIn",
        "/v1/auth/social/signIn",
        "/v1/auth/validate/loginId/{loginId}",
        "/v1/auth/validate/nickname/{nickname}"
    };
}
