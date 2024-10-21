package groom.him.core.auth.util.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class AuthAccessDeniedHandler implements AccessDeniedHandler {
    private AuthAccessDeniedHandler() { }
    private final static AuthAccessDeniedHandler accessDeniedCustomHandler = new AuthAccessDeniedHandler();
    public static AuthAccessDeniedHandler getInstance() {
        return accessDeniedCustomHandler;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{ \"error\": \"해당 유저에게 접근 권한이 없습니다.\" }");
        // TODO: 로그인 오류 프론트 url 정의 후 수정하기
        // response.sendRedirect("/");
    }
}
