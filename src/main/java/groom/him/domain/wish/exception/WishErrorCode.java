package groom.him.domain.wish.exception;

import groom.him.core.exception.HttpErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WishErrorCode implements HttpErrorCode {
    ALREADY_EXISTED_WISH(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 상태입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}
