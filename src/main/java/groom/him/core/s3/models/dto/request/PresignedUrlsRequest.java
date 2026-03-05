package groom.him.core.s3.models.dto.request;

import java.util.List;

public record PresignedUrlsRequest(
    List<PresignedImageRequest> images
) {
    public record PresignedImageRequest(
        String fileName,
        String contentType
    ) {
    }
}