package groom.him.domain.category.enums;

import groom.him.core.exception.HttpErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CategoryErrorCode implements HttpErrorCode {
    CATEGORY_IS_NOT_LEAF(HttpStatus.BAD_REQUEST, "올바르지 않은 카테고리입니다."),
    CATEGORY_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리 입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}