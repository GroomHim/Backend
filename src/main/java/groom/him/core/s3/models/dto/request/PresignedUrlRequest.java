package groom.him.core.s3.models.dto.request;

public record PresignedUrlRequest(
    String fileName,
    String contentType
) {
}