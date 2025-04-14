package groom.him.domain.admin.product.models.dto.request;

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
    String purchaseSiteUrl,
    List<Integer> skinType,
    List<MultipartFile> mainImage,
    List<MultipartFile> contentImage
) {

}