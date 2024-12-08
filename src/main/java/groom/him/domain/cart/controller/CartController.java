package groom.him.domain.cart.controller;

import groom.him.core.dto.Response;
import groom.him.domain.cart.service.CartService;
import groom.him.domain.member.models.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/carts")
@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;

    @PostMapping()
    public Response<Integer> addCart(@AuthenticationPrincipal MemberEntity member,
                                  @RequestParam Integer productId) {
        cartService.addCart(member.getMemberId(), productId);
        return new Response<>(HttpStatus.CREATED.value());
    }
}