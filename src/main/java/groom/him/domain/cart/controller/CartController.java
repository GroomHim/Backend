package groom.him.domain.cart.controller;

import groom.him.core.dto.Response;
import groom.him.domain.cart.models.dto.CartResponse;
import groom.him.domain.cart.service.CartService;
import groom.him.domain.member.models.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/carts")
@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping()
    public Response<CartResponse> saveCart(@AuthenticationPrincipal MemberEntity member,
                                  @RequestParam Integer productId) {
        var response = cartService.saveCart(member.getMemberId(), productId);
        return Response.success(response);
    }
}