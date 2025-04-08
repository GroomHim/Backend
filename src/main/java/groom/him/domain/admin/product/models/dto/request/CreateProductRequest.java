package groom.him.domain.admin.product.models.dto.request;

import jakarta.annotation.Nullable;
import java.util.List;
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
    String purchaseSiteUrl,
    List<Integer> skinType,
    List<MultipartFile> mainImage,
    List<MultipartFile> contentImage
) {
}