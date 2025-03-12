package groom.him.domain.search.models.enums;

import groom.him.core.exception.HttpErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum SearchErrorCode implements HttpErrorCode {
  SEARCH_ID_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 검색내역입니다.");

  private final HttpStatus httpStatus;
  private final String message;

  @Override
  public String getCode() {
    return name();
  }
}
