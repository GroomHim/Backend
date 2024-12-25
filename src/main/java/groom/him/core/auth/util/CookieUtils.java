package groom.him.core.auth.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {
  public static void addRememberMeCookie(HttpServletResponse response, String token) {
    Cookie cookie = new Cookie("Authorization", token);

    // 쿠키 유효 기간 설정 (예: 30일)
    cookie.setMaxAge(2592000); // 30일 = 30 * 24 * 60 * 60초

    // 쿠키가 모든 경로에서 유효하도록 설정
    cookie.setPath("/");

    // 쿠키가 HTTPS 연결에서만 전송되도록 설정
    cookie.setSecure(false);

    // 자바스크립트에서 접근할 수 없도록 설정
    cookie.setHttpOnly(true);

    // 쿠키를 응답에 추가
    response.addCookie(cookie);
  }

}
