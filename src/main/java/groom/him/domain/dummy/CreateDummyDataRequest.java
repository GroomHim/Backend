package groom.him.domain.dummy;

import org.apache.commons.lang3.mutable.Mutable;
import org.springframework.web.multipart.MultipartFile;

public record CreateDummyDataRequest(
    String productName,
    String categoryName,
    String brandName,
    Integer price,
    Float discountRate,
    String deliveryInfo,
    MultipartFile mainImage,
    MultipartFile subImage1,
    MultipartFile subImage2,
    MultipartFile contentImage
) {

}
