package groom.him.domain.order.models.dto.request;

import groom.him.domain.order.models.enums.OrderStatus;

public record ModifyOrderDetailStatusRequest(
    String orderId,
    Integer orderDetailsId,
    OrderStatus orderStatus
) {
}
