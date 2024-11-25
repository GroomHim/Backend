package groom.him.domain.member.controller;

import groom.him.core.dto.Response;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.qa.models.dto.response.QaResponse;
import groom.him.domain.qa.models.enums.QaStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/wish")
    public Response<List<ProductBriefResponse>> findMemberWishList(
        @AuthenticationPrincipal MemberEntity member,
        @RequestParam("skin-type") Boolean isSkinType) {
        return Response.success(memberService.findMemberWishList(member.getMemberId(), isSkinType));
    }

    @GetMapping("/qa")
    public Response<List<QaResponse>> findMemberQaList(
        @AuthenticationPrincipal MemberEntity member,
        @RequestParam(value = "status", required = false) QaStatus qaStatus,
        @RequestParam(value = "start-date", required = false) LocalDate startDate,
        @RequestParam(value = "end-date", required = false) LocalDate endDate) {
        return Response.success(
            memberService.findMemberQaList(member.getMemberId(), qaStatus, startDate, endDate));
    }
}