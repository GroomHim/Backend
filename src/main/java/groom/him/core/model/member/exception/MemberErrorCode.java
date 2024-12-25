package groom.him.core.model.member.exception;

import groom.him.core.exception.HttpErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements HttpErrorCode {
    MEMBER_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    MEMBER_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
    MEMBER_NOT_VALID(HttpStatus.BAD_REQUEST, "잘못된 형식입니다."),
    MEMBER_INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "잘못된 패스워드입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}
