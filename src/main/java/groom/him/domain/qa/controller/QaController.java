package groom.him.domain.qa.controller;

import groom.him.core.dto.Response;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.qa.models.dto.request.QaRequest;
import groom.him.domain.qa.models.dto.response.QaCategoryResponse;
import groom.him.domain.qa.service.QaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/qa")
public class QaController {
    private final QaService qaService;

    public QaController(QaService qaService) {
        this.qaService = qaService;
    }

    @GetMapping("/category")
    public Response<List<QaCategoryResponse>> findQaCategoryList() {
        return Response.success(qaService.findQaCategoryList());
    }

    @PostMapping()
    public Response<Void> addQa(@RequestBody QaRequest request, @AuthenticationPrincipal
    MemberEntity member) {
        qaService.addQa(request, member.getMemberId());
        return new Response(HttpStatus.CREATED.value());
    }
}