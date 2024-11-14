package groom.him.core.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int statusCode,
        String error,
        String message
) {
    public ErrorResponse(int statusCode, String error, String message) {
        this(LocalDateTime.now(), statusCode, error, message);
    }
}