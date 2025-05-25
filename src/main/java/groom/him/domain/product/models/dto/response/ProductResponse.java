package groom.him.domain.product.models.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import groom.him.domain.product.models.entity.ProductEntity;

public record ProductResponse(
    Integer productId,
    String productName,
    Integer categoryId,
    Integer price,
    Float discountRate,
    Integer discountedPrice,
    String brandName,
    String ingredients,
    @JsonInclude(Include.NON_EMPTY)
    String purchaseSiteUrl
) {

    public static ProductResponse of(ProductEntity product) {
        return new ProductResponse(product.getProductId(), product.getProductName(),
            product.getCategory().getCategoryId(), product.getPrice(), product.getDiscountRate(),
            product.getDiscountedPrice(), product.getBrand().getBrandName(),
            product.getIngredients(), product.getPurchaseSiteUrl());
    }
}