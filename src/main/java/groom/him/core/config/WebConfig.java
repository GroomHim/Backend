package groom.him.core.config;

import groom.him.core.auth.service.AuthService;
import groom.him.core.auth.util.JwtTokenProvider;
import groom.him.core.auth.util.interceptor.AutoLoginInterceptor;
import groom.him.core.auth.util.interceptor.RefreshValidateInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthService authService;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods(
            HttpMethod.GET.name(),
            HttpMethod.HEAD.name(),
            HttpMethod.POST.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.DELETE.name(),
            HttpMethod.PUT.name()
        );
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry){
    registry.addInterceptor(new RefreshValidateInterceptor(jwtTokenProvider, authService)).addPathPatterns("/v1/auth/refresh-token");
    registry.addInterceptor(new AutoLoginInterceptor(jwtTokenProvider, authService)).addPathPatterns("/v1/auth/sign-in");

  }
}
