package groom.him.domain.admin.product.models.dto.request;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record ModifyProductImageRequest(
    List<MultipartFile> mainImage,
    List<MultipartFile> contentImage
) {
}