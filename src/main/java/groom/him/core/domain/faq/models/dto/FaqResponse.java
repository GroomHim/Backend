package groom.him.core.domain.faq.models.dto;

import groom.him.core.common.enums.IsPublic;
import java.time.LocalDateTime;

public record FaqResponse(
    Long faqId,
    Long facCategoryId,
    String question,
    String answer,
    String prio,
    IsPublic isPublic,
    LocalDateTime regDt,
    LocalDateTime udtDt
) {

}