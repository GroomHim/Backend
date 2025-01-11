package groom.him.domain.order.models.dto.request;

import groom.him.domain.order.models.entity.OrderDetailEntity;
import groom.him.domain.order.models.enums.OrderStatus;
import groom.him.domain.product.models.entity.ProductEntity;

public record OrderProductInfo(
    Integer productId,
    String productName,
    String brandName,
    String productImgUrl,
    Integer price,
    Integer quantity,
    OrderStatus orderStatus,
    String orderDate
) {

    public static OrderProductInfo of(OrderDetailEntity entity) {
        ProductEntity product = entity.getProduct();

        return new OrderProductInfo(
            product.getProductId(),
            product.getProductName(),
            product.getBrand().getBrandName(),
            product.getImgUrl(),
            entity.getPrice(),
            entity.getQuantity(),
            entity.getOrderStatus(),
            entity.getOrderDate()
        );
    }
}
