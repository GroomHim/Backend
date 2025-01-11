package groom.him.domain.product.models.dto.response;

import groom.him.domain.product.models.entity.ProductEntity;

public record ProductResponse(
    Integer productId,
    String productName,
    Integer price,
    Float discountRate,
    Integer discountedPrice,
    String brandName,
    String ingredients,
    String deliveryInfo
) {
    public static ProductResponse of(ProductEntity product) {
        return new ProductResponse(product.getProductId(), product.getProductName(),
            product.getPrice(), product.getDiscountRate(), product.getDiscountedPrice(),
            product.getBrand().getBrandName(), product.getIngredients(), product.getDeliveryInfo());
    }
}