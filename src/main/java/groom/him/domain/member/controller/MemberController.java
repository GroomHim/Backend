package groom.him.domain.member.controller;

import groom.him.core.dto.Response;
import groom.him.domain.member.models.dto.request.ModifyPasswordRequest;
import groom.him.domain.member.models.dto.request.ValidatePasswordRequest;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @PutMapping("/pwd")
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
}