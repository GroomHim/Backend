package groom.him.product.dto.response;

import groom.him.core.entity.constant.SkinType;
import groom.him.product.entity.ProductEntity;
import java.math.BigDecimal;

public record ProductResponse(
    Long productId,
    String productName,
    BigDecimal price,
    Double discountRate,
    BigDecimal discountedPrice,
    SkinType skinTye
) {

    public static ProductResponse of(ProductEntity entity) {
        return new ProductResponse(
            entity.getProductId(),
            entity.getProductName(),
            entity.getPrice(),
            entity.getDiscountRate(),
            entity.getDiscountedPrice(),
            entity.getSkinType()
        );
    }
}