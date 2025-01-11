package groom.him.domain.wish.controller;

import groom.him.core.dto.Response;
import groom.him.domain.member.models.dto.response.CountResponse;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import groom.him.domain.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/wishes")
public class WishController {
    private final WishService wishService;

    @PostMapping
    public Response<Integer> addWish(@AuthenticationPrincipal MemberEntity member,
                                     @RequestParam Integer productId) {
        wishService.addWish(member.getMemberId(), productId);
        return Response.success();
    }

    @GetMapping
    public Response<Slice<ProductWithWishResponse>> findMemberWishList(
        @AuthenticationPrincipal MemberEntity member,
        @RequestParam("skin-type") Boolean isSkinType,
        Pageable pageable) {
        return Response.success(
            wishService.findMemberProductWishList(member.getMemberId(), isSkinType, pageable));
    }

    @GetMapping("/count")
    public Response<CountResponse> findMemberWishCount(
        @AuthenticationPrincipal MemberEntity member) {
        return Response.success(wishService.findMemberWishCount(member.getMemberId()));
    }

    @DeleteMapping
    public Response<Integer> deleteWish(@AuthenticationPrincipal MemberEntity member,
                                        @RequestParam Integer productId) {
        wishService.deleteWish(member.getMemberId(), productId);
        return Response.success();
    }
}
