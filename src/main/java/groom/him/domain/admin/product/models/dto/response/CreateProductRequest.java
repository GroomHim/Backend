package groom.him.domain.admin.product.models.dto.response;

import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

public record CreateProductRequest(
    String productName,
    Integer categoryId,
    Integer brandId,
    Integer price,
    Float discountRate,
    Integer discountedPrice,
    String ingredients,
    String deliveryInfo,
    @Nullable
    String purchaseSiteUrl,
    Integer[] skinType,
    MultipartFile[] mainImage,
    MultipartFile[] contentImage
) {
}