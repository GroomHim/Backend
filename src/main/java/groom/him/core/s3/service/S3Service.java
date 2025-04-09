package groom.him.core.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import groom.him.domain.product.models.enums.ImgType;
import groom.him.core.s3.exception.S3Exception;
import groom.him.core.s3.models.enums.S3ErrorCode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.profiles.active}")
    private String profiles;

    private static final String PRODUCT = "product";

    public List<String> uploadProductImages(List<MultipartFile> multipartFiles, Integer productId,
        ImgType type) {
        return uploadImages(multipartFiles, productId, type, PRODUCT);
    }

    private List<String> uploadImages(List<MultipartFile> multipartFiles, Integer id, ImgType type,
        String path) {
        List<String> uploadImageUrls = new ArrayList<>();

        for (MultipartFile image : multipartFiles) {
            if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
                throw new S3Exception(S3ErrorCode.EMPTY_FILE_EXCEPTION);
            }

            String fileName = String.format("%s/%s/%d/%s/%s-%s",
                profiles, path, id, type, UUID.randomUUID(), image.getOriginalFilename());

            String uploadImageUrl = uploadImageToS3(image, fileName);
            uploadImageUrls.add(uploadImageUrl);
        }

        return uploadImageUrls;
    }

    private String uploadImageToS3(MultipartFile image, String fileName) {
        byte[] bytes;

        try (InputStream is = image.getInputStream()) {
            bytes = IOUtils.toByteArray(is);
        } catch (IOException e) {
            log.error(e.toString());
            throw new S3Exception(S3ErrorCode.PUT_OBJECT_EXCEPTION);
        }

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(bytes.length);

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName,
                byteArrayInputStream, metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
        } catch (IOException e) {
            log.error(e.toString());
            throw new S3Exception(S3ErrorCode.PUT_OBJECT_EXCEPTION);
        }

        return amazonS3.getUrl(bucket, fileName).toString();
    }
}