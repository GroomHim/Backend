package groom.him.domain.qa.exception;

import groom.him.core.exception.BusinessException;
import groom.him.core.exception.HttpErrorCode;

public class QaException extends BusinessException {
    public QaException(HttpErrorCode httpErrorCode) {
        super(httpErrorCode);
    }
}