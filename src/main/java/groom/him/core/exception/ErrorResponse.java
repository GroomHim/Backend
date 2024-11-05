package groom.him.core.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String error;
    private final String message;

    public ErrorResponse(CommonErrorCode errorCode) {
        this.statusCode = errorCode.getHttpStatus().value();
        this.error = errorCode.getHttpStatus().name();
        this.message = errorCode.getMessage();
    }
}