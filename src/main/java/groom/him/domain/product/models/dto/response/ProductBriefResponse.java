package groom.him.domain.product.models.dto.response;

import groom.him.domain.product.models.entity.ProductEntity;
import java.util.List;

public record ProductBriefResponse(
    Integer productId,
    String productName,
    Integer price,
    Float discountRate,
    Integer discountedPrice,
    String imgUrl,
    List<String> skinType
) {
    public static ProductBriefResponse of(ProductEntity product, List<String> skinType) {
        return new ProductBriefResponse(product.getProductId(), product.getProductName(),
            product.getPrice(), product.getDiscountRate(), product.getDiscountedPrice(),
            product.getImgUrl(), skinType
        );
    }
}