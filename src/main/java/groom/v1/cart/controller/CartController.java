//package groom.v1.cart.controller;
//
//import groom.him.core.models.dto.Response;
//import groom.v1.cart.models.dto.request.ModifyCartCountRequest;
//import groom.v1.cart.models.dto.response.CartResponse;
//import groom.v1.cart.models.dto.response.CartsResponse;
//import groom.v1.cart.service.CartService;
//import groom.him.domain.member.models.dto.response.CountResponse;
//import groom.him.domain.member.models.entity.MemberEntity;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequestMapping("/v1/carts")
//@RequiredArgsConstructor
//@RestController
//public class CartController {
//    private final CartService cartService;
//
//    @GetMapping
//    public Response<List<CartsResponse>> findCarts(@AuthenticationPrincipal MemberEntity member) {
//        List<CartsResponse> carts = cartService.findCarts(member.getMemberId());
//        return Response.success(carts);
//    }
//
//    @PostMapping()
//    public Response<CartResponse> saveCart(@AuthenticationPrincipal MemberEntity member,
//                                           @RequestParam Integer productId) {
//        var response = cartService.saveCart(member.getMemberId(), productId);
//        return Response.success(response);
//    }
//
//    @PatchMapping("/count")
//    public Response<List<CartResponse>> modifyCartsCount(
//        @AuthenticationPrincipal MemberEntity member,
//        @RequestBody List<ModifyCartCountRequest> modifyCartCountRequest) {
//        var response = cartService.modifyCartsCount(member.getMemberId(), modifyCartCountRequest);
//        return Response.success(response);
//    }
//
//    @DeleteMapping()
//    public Response<Integer> deleteCart(@AuthenticationPrincipal MemberEntity member,
//                                        @RequestParam Integer cartId) {
//        cartService.deleteCart(member.getMemberId(), cartId);
//        return new Response<>(HttpStatus.NO_CONTENT.value());
//    }
//
//    @DeleteMapping("/selected-items")
//    public Response<Integer> deleteCarts(@AuthenticationPrincipal MemberEntity member,
//                                         @RequestParam List<Integer> cartIds) {
//        cartService.deleteCarts(member.getMemberId(), cartIds);
//        return new Response<>(HttpStatus.NO_CONTENT.value());
//    }
//
//    @GetMapping("/count")
//    public Response<CountResponse> findMemberCartCount(
//        @AuthenticationPrincipal MemberEntity member) {
//        return Response.success(cartService.findMemberCartCount(member.getMemberId()));
//    }
//}