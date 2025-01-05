package groom.him.domain.order.models.dto.request;

import groom.him.domain.order.models.entity.OrderDetailEntity;
import groom.him.domain.order.models.enums.OrderStatus;

public record OrderProductInfo(
    Integer productId,
    Integer price,
    Integer quantity,
    String productImgUrl,
    OrderStatus orderStatus,
    String orderDate
) {

    public static OrderProductInfo from(OrderDetailEntity entity) {
        return new OrderProductInfo(
            entity.getProduct().getProductId(),
            entity.getPrice(),
            entity.getQuantity(),
            entity.getProductImgUrl(),
            entity.getOrderStatus(),
            entity.getOrderDate()
        );
    }
}
