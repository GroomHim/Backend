package groom.him.domain.wish.controller;

import groom.him.core.dto.Response;
import groom.him.domain.member.models.dto.response.CountResponse;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.product.models.dto.response.ProductWithWishResponse;
import groom.him.domain.wish.service.WishService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/wishes")
public class WishController {
    private final WishService wishService;

    @PostMapping
    public Response<Integer> addWish(@AuthenticationPrincipal MemberEntity member,
        @RequestParam Integer productId) {
        wishService.addWish(member.getMemberId(), productId);
        return new Response<>(HttpStatus.CREATED.value());
    }

    @GetMapping
    public Response<Slice<ProductWithWishResponse>> findMemberWishList(
        @AuthenticationPrincipal MemberEntity member,
        @RequestParam("skinType") Boolean isSkinType,
        Pageable pageable) {
        return Response.success(
            wishService.findWishListByMemberId(member.getMemberId(), isSkinType, pageable));
    }

    @GetMapping("/count")
    public Response<CountResponse> findMemberWishCount(
        @AuthenticationPrincipal MemberEntity member) {
        return Response.success(wishService.findWishCountByMemberId(member.getMemberId()));
    }

    @DeleteMapping
    public Response<Integer> deleteWish(@AuthenticationPrincipal MemberEntity member,
        @RequestParam Integer productId) {
        wishService.deleteWish(member.getMemberId(), productId);
        return new Response<>(HttpStatus.NO_CONTENT.value());
    }
}