package groom.him.domain.order.models.dto.response;

import groom.him.domain.order.models.dto.request.OrderProductInfo;

import java.util.List;

public record OrderBriefResponse(
    String orderId,
    List<OrderProductInfo> productInfos
) {
}