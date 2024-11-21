package groom.him.domain.notice.models.dto.response;

import groom.him.core.common.enums.IsPublic;
import groom.him.domain.notice.models.entity.NoticeEntity;
import java.time.LocalDateTime;

public record NoticeResponse(
    Long noticeId,
    Integer memberId,
    String title,
    String content,
    IsPublic isPublic,
    LocalDateTime regDt,
    LocalDateTime udtDt
) {

    public static NoticeResponse of(NoticeEntity notice) {
        return new NoticeResponse(notice.getNoticeId(), notice.getMember().getMemberId(),
            notice.getTitle(), notice.getContent(), notice.getIsPublic(), notice.getRegDt(),
            notice.getUdtDt()
        );
    }
}