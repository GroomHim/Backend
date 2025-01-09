package groom.him.domain.faq.models.dto.response;

import groom.him.core.common.enums.IsPublic;
import java.time.LocalDateTime;

public record FaqResponse(
    Integer faqId,
    String category,
    String question,
    String answer,
    String prio,
    IsPublic isPublic,
    LocalDateTime regDt,
    LocalDateTime uptDt
) {
}