package groom.him.domain.s3.exception;

import groom.him.core.exception.BusinessException;
import groom.him.core.exception.HttpErrorCode;

public class S3Exception extends BusinessException {
    public S3Exception(HttpErrorCode httpErrorCode) {
        super(httpErrorCode);
    }
}