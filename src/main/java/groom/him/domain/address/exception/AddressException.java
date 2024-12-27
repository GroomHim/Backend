package groom.him.domain.address.exception;

import groom.him.core.exception.BusinessException;
import groom.him.core.exception.HttpErrorCode;

public class AddressException extends BusinessException {
    public AddressException(HttpErrorCode httpErrorCode) {
        super(httpErrorCode);
    }
}