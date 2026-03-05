package groom.him.core.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import groom.him.domain.product.models.enums.ImgType;
import groom.him.core.s3.exception.S3Exception;
import groom.him.core.s3.models.enums.S3ErrorCode;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class S3Service {
    private final AmazonS3 amazonS3;

    private final String bucket;

    private final Executor uploadExecutor;

    @Value("${spring.profiles.active}")
    private String profiles;

    private static final String PRODUCT = "product";


    public S3Service(AmazonS3 amazonS3, @Qualifier("uploadExecutor") Executor uploadExecutor,
        String bucket) {
        this.amazonS3 = amazonS3;
        this.uploadExecutor = uploadExecutor;
        this.bucket = bucket;
    }

    public List<String> uploadProductImages(List<MultipartFile> multipartFiles, Integer productId,
        ImgType type) {
        return uploadImagesSequential(multipartFiles, productId, type, PRODUCT);
    }

    // 직렬, 병렬 테스트용 메소드
    public List<String> uploadImagesWithMode(List<MultipartFile> multipartFiles, Integer productId,
        ImgType type, String mode) {
        if (mode.equals("PARALLEL")) {
            return uploadImagesParallel(multipartFiles, productId, type, PRODUCT);
        }
        return uploadImagesSequential(multipartFiles, productId, type, PRODUCT);
    }

    // 직렬 업로드
    public List<String> uploadImagesSequential(List<MultipartFile> multipartFiles, Integer id,
        ImgType type, String path) {
        List<String> uploadImageUrls = new ArrayList<>();

        for (MultipartFile image : multipartFiles) {
            String uploadImageUrl = uploadSingleImage(image, id, type, path);
            uploadImageUrls.add(uploadImageUrl);
        }

        return uploadImageUrls;
    }

    // 병렬 업로드
    public List<String> uploadImagesParallel(List<MultipartFile> multipartFiles, Integer id,
        ImgType type, String path) {
        List<CompletableFuture<String>> futures = multipartFiles.stream()
            .map(image ->
                CompletableFuture.supplyAsync(
                    () -> uploadSingleImage(image, id, type, path), uploadExecutor
                )
            ).toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return futures.stream()
            .map(CompletableFuture::join)
            .toList();
    }

    private String uploadSingleImage(MultipartFile image, Integer id, ImgType type, String path) {
        if (image.isEmpty()) {
            throw new S3Exception(S3ErrorCode.EMPTY_FILE_EXCEPTION);
        }
        try (InputStream inputStream = image.getInputStream()) {
            String fileName = String.format("%s/%s/%d/%s/%s-%s",
                profiles, path, id, type, UUID.randomUUID(), image.getOriginalFilename());

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(image.getContentType());
            metadata.setContentLength(image.getSize());

            PutObjectRequest request = new PutObjectRequest(bucket, fileName, inputStream,
                metadata);
            amazonS3.putObject(request);

            return amazonS3.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new S3Exception(S3ErrorCode.PUT_OBJECT_EXCEPTION);
        }
    }
}