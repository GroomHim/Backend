package groom.him.domain.member.exception;

import groom.him.core.exception.BusinessException;
import groom.him.core.exception.HttpErrorCode;

public class WishException extends BusinessException {
    public WishException(HttpErrorCode httpErrorCode) {
        super(httpErrorCode);
    }
}
