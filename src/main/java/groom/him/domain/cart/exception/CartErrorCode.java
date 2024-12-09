package groom.him.domain.cart.exception;

import groom.him.core.exception.HttpErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CartErrorCode implements HttpErrorCode {
    CART_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 장바구니 정보입니다."),
    CART_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "해당 장바구니를 삭제할 권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}
