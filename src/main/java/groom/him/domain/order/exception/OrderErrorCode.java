package groom.him.domain.order.exception;

import groom.him.core.exception.HttpErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderErrorCode implements HttpErrorCode {
    NOT_ENOUGH_POINTS(HttpStatus.BAD_REQUEST, "포인트가 적습니다."),
    DUPLICATED_ORDER_ID(HttpStatus.BAD_REQUEST, "주문번호가 이미 존재합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}
