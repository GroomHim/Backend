package groom.him.core.model.user.exception;

import groom.him.core.exception.BusinessException;
import groom.him.core.exception.HttpErrorCode;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class MemberException {

    public static class MemberNotExistException extends BusinessException {
        public MemberNotExistException(HttpErrorCode httpErrorCode){
            super(httpErrorCode);
        }
    }
}
