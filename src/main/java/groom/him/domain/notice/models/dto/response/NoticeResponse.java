package groom.him.domain.notice.models.dto.response;

import groom.him.core.common.enums.IsPublic;
import java.time.LocalDateTime;

public record NoticeResponse(
    Long noticeId,
    Long memberId,
    String title,
    String content,
    IsPublic isPublic,
    LocalDateTime regDt,
    LocalDateTime udtDt
) {

}