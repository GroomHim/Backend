package groom.him.domain.order.models.dto.response;

import groom.him.domain.order.models.entity.OrderDetailEntity;
import groom.him.domain.order.models.enums.OrderStatus;
import groom.him.domain.product.models.entity.ProductEntity;

public record OrderDetailResponse(
    Integer productId,
    String productName,
    String brandName,
    String productImgUrl,
    Integer orderDetailId,
    Integer price,
    Integer quantity,
    OrderStatus orderStatus,
    String orderDate
) {

    public static OrderDetailResponse of(OrderDetailEntity entity) {
        ProductEntity product = entity.getProduct();

        return new OrderDetailResponse(
            product.getProductId(),
            product.getProductName(),
            product.getBrand().getBrandName(),
            product.getImgUrl(),
            entity.getOrderDetailId(),
            entity.getPrice(),
            entity.getQuantity(),
            entity.getOrderStatus(),
            entity.getOrderDate()
        );
    }
}
