package groom.him.domain.admin.product.models.dto.request;

import jakarta.annotation.Nullable;
import java.util.List;

public record ModifyProductRequest(
    String productName,
    Integer categoryId,
    Integer brandId,
    Integer price,
    Float discountRate,
    Integer discountedPrice,
    String ingredients,
    String purchaseSiteUrl,
    List<Integer> skinType
) {
}