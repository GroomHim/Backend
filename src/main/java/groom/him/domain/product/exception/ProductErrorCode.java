package groom.him.domain.product.exception;

import groom.him.core.exception.HttpErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements HttpErrorCode {
    PRODUCT_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 상품 정보입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}
