package groom.him.domain.order.controller;

import groom.him.core.dto.Response;
import groom.him.domain.member.models.entity.MemberEntity;
import groom.him.domain.order.models.dto.request.AddOrderRequest;
import groom.him.domain.order.models.dto.response.OrderBriefResponse;
import groom.him.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    @GetMapping("{orderId}")
    public Response<OrderBriefResponse> findOrderBrief(@PathVariable String orderId) {
        var response = orderService.findOrderBrief(orderId);
        return Response.success(response);
    }

    @GetMapping("/orderId")
    public Response<String> getOrder() {
        String orderNum = orderService.getOrderId();
        return Response.success(orderNum);
    }

    @PostMapping()
    public Response<Integer> addOrder(
        @RequestBody AddOrderRequest request,
        @AuthenticationPrincipal MemberEntity member
    ) {
        orderService.addOrder(member.getMemberId(), request);
        return Response.success();
    }
}
