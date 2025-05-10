package groom.him.domain.member.controller;

import groom.him.core.models.dto.Response;
import groom.him.domain.member.models.dto.request.CancelMemberRequest;
import groom.him.domain.member.models.dto.request.ModifyMyInfoRequest;
import groom.him.domain.member.models.dto.request.ModifyPasswordRequest;
import groom.him.domain.member.models.dto.request.ValidatePasswordRequest;
import groom.him.domain.member.models.dto.response.MemberResponse;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.qa.models.dto.response.QaResponse;
import groom.him.domain.qa.models.enums.QaStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/info")
    public Response<MemberResponse> findMyInfo(@AuthenticationPrincipal MemberEntity member) {
        var response = memberService.findMyInfo(member.getMemberId());
        return Response.success(response);
    }

    @PatchMapping
    public Response<MemberResponse> modifyMyInfo(@AuthenticationPrincipal MemberEntity member,
        @RequestBody ModifyMyInfoRequest request) {
        var response = memberService.modifyMyInfo(member.getMemberId(), request);
        return Response.success(response);
    }

    @DeleteMapping
    public Response<Integer> cancelMember(@AuthenticationPrincipal MemberEntity member,
        @RequestBody CancelMemberRequest request) {
        memberService.softDelete(member.getMemberId(), request);
        return new Response<>(HttpStatus.NO_CONTENT.value());
    }

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

    @GetMapping("/qa")
    public Response<List<QaResponse>> findMemberQaList(
        @AuthenticationPrincipal MemberEntity member,
        @RequestParam(value = "status", required = false) QaStatus qaStatus,
        @RequestParam(value = "startDate", required = false) LocalDate startDate,
        @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return Response.success(
            memberService.findMemberQaList(member.getMemberId(), qaStatus, startDate, endDate));
    }
}
