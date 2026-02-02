package groom.him.core.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import groom.him.core.s3.models.dto.request.PresignedUrlsRequest;
import groom.him.core.s3.models.dto.response.PresignedUrlResponse;
import groom.him.core.s3.models.dto.response.PresignedUrlsResponse;
import groom.him.core.s3.models.dto.response.PresignedUrlsResponse.PresignedUrlInfo;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class S3PresignedUrlService {

    private final AmazonS3 amazonS3;
    private final String bucket;
    @Value("${spring.profiles.active}")
    private String profiles;
    private static final String PRODUCT = "product";


    public S3PresignedUrlService(
        AmazonS3 amazonS3,
        @Value("${app.s3.bucket}") String bucket
    ) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }

    public PresignedUrlResponse generateUploadUrl(Integer productId, String originalFileName,
        String contentType) {
        String key = String.format("/%s/%s/%d/%s-%s",
            profiles, PRODUCT, productId, UUID.randomUUID(), originalFileName);

        Date expiration = new Date(System.currentTimeMillis() + 5 * 60 * 1000); // 5분

        GeneratePresignedUrlRequest request =
            new GeneratePresignedUrlRequest(bucket, key)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        request.addRequestParameter(Headers.CONTENT_TYPE, contentType);

        URL url = amazonS3.generatePresignedUrl(request);
        return new PresignedUrlResponse(url.toString());
    }

    public PresignedUrlsResponse generateUploadUrls(Integer productId,
        List<PresignedUrlsRequest.PresignedImageRequest> images
    ) {
        Date expiration = new Date(System.currentTimeMillis() + 5 * 60 * 1000);

        List<PresignedUrlInfo> results = images.stream()
            .map(image -> {
                String key = String.format("%s/%s/%d/%s-%s",
                    profiles, PRODUCT, productId, UUID.randomUUID(), image.fileName());

                GeneratePresignedUrlRequest request =
                    new GeneratePresignedUrlRequest(bucket, key)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration);

                request.addRequestParameter(Headers.CONTENT_TYPE, image.contentType());

                URL url = amazonS3.generatePresignedUrl(request);

                return new PresignedUrlsResponse.PresignedUrlInfo(
                    image.fileName(), key, url.toString());
            }).toList();

        return new PresignedUrlsResponse(results);
    }
}