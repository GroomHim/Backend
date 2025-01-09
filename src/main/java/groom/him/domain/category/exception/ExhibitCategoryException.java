package groom.him.domain.category.exception;

import groom.him.core.exception.BusinessException;
import groom.him.core.exception.HttpErrorCode;

public class ExhibitCategoryException extends BusinessException {
    public ExhibitCategoryException(HttpErrorCode httpErrorCode) {
        super(httpErrorCode);
    }
}