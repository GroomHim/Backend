package groom.him.domain.product.exception;

import groom.him.core.exception.BusinessException;
import groom.him.core.exception.HttpErrorCode;

public class BrandException extends BusinessException {
    public BrandException(HttpErrorCode httpErrorCode) {
        super(httpErrorCode);
    }
}