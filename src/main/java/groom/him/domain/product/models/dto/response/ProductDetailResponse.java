package groom.him.domain.product.models.dto.response;

import java.util.List;

public record ProductDetailResponse(
    ProductResponse product,
    Boolean isWish,
    List<String> mainImage,
    List<String> contentImage
) {
    public static ProductDetailResponse of(ProductResponse product, Boolean isWish,
        List<String> mainImg, List<String> contentImg) {
        return new ProductDetailResponse(product, isWish, mainImg, contentImg);
    }
}