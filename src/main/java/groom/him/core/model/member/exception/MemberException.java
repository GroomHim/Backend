package groom.him.core.model.member.exception;

import groom.him.core.exception.BusinessException;
import groom.him.core.exception.HttpErrorCode;

public class MemberException extends BusinessException{

    public MemberException(HttpErrorCode httpErrorCode) {
        super(httpErrorCode);
    }
}
