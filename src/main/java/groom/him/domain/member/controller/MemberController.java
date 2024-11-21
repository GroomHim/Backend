package groom.him.domain.member.controller;

import groom.him.core.dto.Response;
import groom.him.domain.member.service.MemberService;
import groom.him.domain.product.models.dto.response.ProductBriefResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{memberId}/wish")
    public Response<List<ProductBriefResponse>> findMemberWishList(
        @PathVariable("memberId") Integer memberId, @RequestParam("skin-type") Boolean isSkinType) {
        List<ProductBriefResponse> data = memberService.findMemberWishList(memberId, isSkinType);
        return Response.success(data);
    }
}