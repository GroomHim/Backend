package groom.him.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommonException extends RuntimeException {
    private final HttpErrorCode httpErrorCode;
}