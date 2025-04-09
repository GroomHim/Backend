package groom.him.core.s3.models.enums;

import groom.him.core.exception.HttpErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum S3ErrorCode implements HttpErrorCode {
    EMPTY_FILE_EXCEPTION(HttpStatus.NOT_FOUND, "파일이 존재하지 않습니다."),
    INPUT_STREAM_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "업로드 파일을 읽는 중 오류가 발생했습니다."),
    PUT_OBJECT_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "S3 Object 업로드에 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}