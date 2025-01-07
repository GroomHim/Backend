package groom.him.domain.product.models.dto.response;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import groom.him.domain.product.models.entity.ProductEntity;
import groom.him.domain.product.models.entity.QProductEntity;

public record ProductBriefResponse(
    Integer productId,
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
            product.getPrice(), product.getDiscountRate(), product.getDiscountedPrice(),
            product.getImgUrl()
        );
    }
}