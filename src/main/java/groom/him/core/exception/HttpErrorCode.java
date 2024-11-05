package groom.him.core.exception;

import org.springframework.http.HttpStatus;

public interface HttpErrorCode {
    HttpStatus getHttpStatus();

    String getCode();

    String getMessage();
}