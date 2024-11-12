package groom.him.core.model.member.exception;

import groom.him.core.exception.HttpErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements HttpErrorCode {
    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "잘못된 요청입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}
