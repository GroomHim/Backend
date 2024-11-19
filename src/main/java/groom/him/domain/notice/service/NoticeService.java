package groom.him.domain.notice.service;

import groom.him.core.common.enums.IsPublic;
import groom.him.domain.notice.models.dto.response.NoticeResponse;
import groom.him.domain.notice.models.entity.NoticeEntity;
import groom.him.domain.notice.repository.NoticeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<NoticeResponse> getNoticeList() {
        List<NoticeEntity> noticeEntityList = noticeRepository.findAllByIsPublicOrderByRegDtDesc(
            IsPublic.OPEN);
        return noticeEntityList.stream()
            .map(NoticeResponse::of)
            .toList();
    }
}