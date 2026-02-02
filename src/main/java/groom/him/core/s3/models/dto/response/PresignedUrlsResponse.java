package groom.him.core.s3.models.dto.response;

import java.util.List;

public record PresignedUrlsResponse(
    List<PresignedUrlInfo> urls
) {
    public record PresignedUrlInfo(
        String fileName,
        String key,
        String uploadUrl
    ) {
    }
}