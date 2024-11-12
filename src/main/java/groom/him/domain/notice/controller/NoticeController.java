package groom.him.domain.notice.controller;

import groom.him.domain.notice.service.NoticeService;
import groom.him.domain.notice.models.dto.response.NoticeResponse;
import groom.him.core.dto.Response;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notice")
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public Response<List<NoticeResponse>> getNoticeList() {
        return Response.success(noticeService.getNoticeList());
    }
}