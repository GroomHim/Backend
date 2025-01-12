package groom.him.domain.order.models.dto.response;

import java.util.List;

public record OrderBriefResponse(
    String orderId,
    List<OrderDetailResponse> products
) {
}