package groom.him.domain.address.models.enums;

import groom.him.core.exception.HttpErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AddressErrorCode implements HttpErrorCode {
    MAX_ADDRESS_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "배송지는 최대 10개까지 등록 가능합니다."),
    ADDRESS_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 배송지입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}