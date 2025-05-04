package groom.him.domain.product.models.dto.response;

import groom.him.domain.product.models.entity.ProductEntity;

public record ProductBriefResponse(
    Integer productId,
    String categoryName,
    String productName,
    String brandName,
    Integer price,
    Float discountRate,
    Integer discountedPrice,
    String imgUrl
) {

    public static ProductBriefResponse of(ProductEntity product) {
        return new ProductBriefResponse(
            product.getProductId(), product.getProductName(), product.getBrand().getBrandName(),
            product.getCategory().getCategoryName(),
            product.getPrice(), product.getDiscountRate(), product.getDiscountedPrice(),
            product.getImgUrl()
        );
    }
}