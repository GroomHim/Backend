package groom.him.domain.order.controller;

import groom.him.core.dto.Response;
import groom.him.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orderId")
    public Response<String> getOrderId() {
        String orderNum = orderService.getOrderId();
        return Response.success(orderNum);
    }
}
