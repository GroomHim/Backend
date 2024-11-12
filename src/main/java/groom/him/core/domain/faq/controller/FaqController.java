package groom.him.core.domain.faq.controller;

import groom.him.core.domain.faq.models.dto.FaqResponse;
import groom.him.core.domain.faq.service.FaqService;
import groom.him.core.dto.Response;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/faq")
public class FaqController {
    private final FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public Response<List<FaqResponse>> getFaqList() {
        return Response.success(faqService.getFaqList());
    }
}