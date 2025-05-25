package groom.him.domain.admin.product.models.dto.response;

import groom.him.domain.product.models.dto.response.ProductResponse;
import java.util.List;

public record AdminProductDetailResponse(
    ProductResponse product,
    List<Integer> skinType,
    List<String> mainImage,
    List<String> contentImage
) {
    public static AdminProductDetailResponse of(ProductResponse product, List<Integer> skinType,
        List<String> mainImg, List<String> contentImg) {
        return new AdminProductDetailResponse(product, skinType, mainImg, contentImg);
    }
}