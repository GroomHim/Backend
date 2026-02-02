package groom.him.core.s3.models.dto.request;

import groom.him.domain.product.models.enums.ImgType;
import java.util.List;

public record UploadCompleteRequest(
    List<UploadedImageInfo> images
) {
    public record UploadedImageInfo(
        String key,
        ImgType type
    ) {
    }
}