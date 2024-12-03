package groom.him.domain.member.controller;

import groom.him.core.dto.Response;
import groom.him.domain.member.models.dto.request.ModifyPasswordRequest;
import groom.him.domain.member.models.dto.request.ValidatePasswordRequest;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import groom.him.domain.qa.models.dto.response.QaResponse;
import groom.him.domain.qa.models.enums.QaStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberController {
    private final MemberService memberService;

    @PatchMapping("/pwd")
    public Response<Integer> modifyPassword(@AuthenticationPrincipal MemberEntity member,
        @RequestBody ModifyPasswordRequest request) {
        memberService.modifyPassword(member.getMemberId(), request.newPassword());
        return Response.success();
    }

    @PostMapping("/validate/pwd")
    public Response<Integer> validatePassword(@AuthenticationPrincipal MemberEntity member,
        @RequestBody ValidatePasswordRequest request) {
        memberService.validatePassword(member.getMemberId(), request.password());
        return Response.success();
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