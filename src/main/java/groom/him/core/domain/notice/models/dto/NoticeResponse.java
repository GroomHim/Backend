package groom.him.core.domain.notice.models.dto;

import groom.him.core.common.enums.IsPublic;
import java.time.LocalDateTime;

public record NoticeListResponse(
    Long noticeId,
    Long memberId,
    String title,
    String content,
    IsPublic isPublic,
    LocalDateTime regDt,
    LocalDateTime udtDt
) {

}