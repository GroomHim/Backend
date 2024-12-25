package groom.him.core.auth.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

  public static void addRememberMeCookie(HttpServletResponse response, String token) {
    Cookie cookie = new Cookie("Authorization", token);
    cookie.setMaxAge(2592000);
    cookie.setPath("/");
    // 쿠키가 HTTPS 연결에서만 전송되도록 설정
    cookie.setSecure(false);
    // 자바스크립트에서 접근할 수 없도록 설정
    cookie.setHttpOnly(true);
    response.addCookie(cookie);
  }
}
