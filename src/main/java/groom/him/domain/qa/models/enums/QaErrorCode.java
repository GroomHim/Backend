package groom.him.domain.qa.models.enums;

import groom.him.core.exception.HttpErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum QaErrorCode implements HttpErrorCode {
    QA_CATEGORY_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 문의내역 카테고리입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}