package groom.him.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return ResponseEntity.status(HttpStatus.OK).body(makeErrorResponse(e.getHttpErrorCode()));
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException e) {
        return ResponseEntity.status(e.getHttpErrorCode().getHttpStatus())
            .body(makeErrorResponse(e.getHttpErrorCode()));
    }

    private ErrorResponse makeErrorResponse(HttpErrorCode errorCode) {
        return ErrorResponse.builder()
            .statusCode(errorCode.getHttpStatus().value())
            .error(errorCode.getCode())
            .message(errorCode.getMessage())
            .build();
    }
}