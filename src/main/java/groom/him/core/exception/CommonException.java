package groom.him.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class CommonException extends RuntimeException {
    private final HttpErrorCode httpErrorCode;
}