package groom.him.domain.member.controller;

import groom.him.core.auth.dto.request.ModifyPasswordRequest;
import groom.him.core.dto.Response;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PutMapping("/pwd")
    public Response<Integer> modifyPassword(@AuthenticationPrincipal MemberEntity member,
        @RequestBody ModifyPasswordRequest request) {
        memberService.modifyPassword(member, request.newPassword());
        return Response.success();
    }
}
