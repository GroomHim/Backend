package groom.him.common.models.constant;

import groom.him.core.exception.HttpErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SkinTypeErrorCode implements HttpErrorCode {
    SKIN_TYPE_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 피부타입입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}