package groom.him.common.exception;

import groom.him.core.exception.BusinessException;
import groom.him.core.exception.HttpErrorCode;

public class SkinTypeException extends BusinessException {
    public SkinTypeException(HttpErrorCode httpErrorCode) {
        super(httpErrorCode);
    }
}