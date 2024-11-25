package groom.him.domain.qa.models.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import groom.him.domain.qa.models.enums.QaStatus;
import java.time.LocalDateTime;

@JsonInclude(Include.NON_NULL)
public record QaResponse(
    Integer qaId,
    String categoryName,
    String title,
    String content,
    String answer,
    QaStatus status,
    LocalDateTime regDt
) {

    public static QaResponse of(Integer qaId, String categoryName, String title, String content,
        String answer, QaStatus status, LocalDateTime regDt) {
        return new QaResponse(qaId, categoryName, title, content, answer, status, regDt);
    }
}