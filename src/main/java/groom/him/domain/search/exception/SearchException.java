package groom.him.domain.search.exception;

import groom.him.core.exception.BusinessException;
import groom.him.core.exception.HttpErrorCode;

public class SearchException extends BusinessException {

  public SearchException(HttpErrorCode httpErrorCode) {
    super(httpErrorCode);
  }
}
