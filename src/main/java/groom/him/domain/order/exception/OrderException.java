package groom.him.domain.order.exception;

import groom.him.core.exception.BusinessException;
import groom.him.core.exception.HttpErrorCode;

public class OrderException extends BusinessException {
    public OrderException(HttpErrorCode httpErrorCode) {
        super(httpErrorCode);
    }
}
