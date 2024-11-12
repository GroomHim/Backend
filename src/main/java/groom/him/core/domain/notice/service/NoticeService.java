package groom.him.core.domain.notice.service;

import groom.him.core.common.enums.IsPublic;
import groom.him.core.domain.notice.models.dto.NoticeResponse;
import groom.him.core.domain.notice.models.entities.NoticeEntity;
import groom.him.core.domain.notice.repository.NoticeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<NoticeResponse> getNoticeList() {
        List<NoticeEntity> noticeEntityList = noticeRepository.findAllByIsPublicOrderByRegDtDesc(IsPublic.OPEN);
        return noticeEntityList.stream()
            .map(notice -> new NoticeResponse(
                notice.getNoticeId(), notice.getMemberId(), notice.getTitle(), notice.getContent(),
                notice.getIsPublic(), notice.getRegDt(), notice.getUpdDt())
            )
            .toList();
    }
}