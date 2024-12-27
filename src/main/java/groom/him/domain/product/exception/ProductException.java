package groom.him.domain.product.exception;

import groom.him.core.exception.BusinessException;
import groom.him.core.exception.HttpErrorCode;

public class ProductException extends BusinessException {
    public ProductException(HttpErrorCode httpErrorCode) {
        super(httpErrorCode);
    }
}
