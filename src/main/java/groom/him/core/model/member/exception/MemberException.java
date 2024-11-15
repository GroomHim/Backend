package groom.him.core.model.member.exception;

import groom.him.core.exception.BusinessException;
import groom.him.core.exception.HttpErrorCode;

public class MemberException {

    public static class MemberNotExistException extends BusinessException {
        public MemberNotExistException(HttpErrorCode httpErrorCode) {
            super(httpErrorCode);
        }
    }

    public static class MemberDuplicatedException extends BusinessException {
        public MemberDuplicatedException(HttpErrorCode httpErrorCode) {
            super(httpErrorCode);
        }
    }
}
