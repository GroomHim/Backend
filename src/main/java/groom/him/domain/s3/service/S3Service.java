package groom.him.domain.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import groom.him.domain.s3.exception.S3Exception;
import groom.him.domain.s3.models.enums.S3ErrorCode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public String saveImage(MultipartFile image, Integer memberId, String dirName)
        throws IOException {
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new S3Exception(S3ErrorCode.EMPTY_FILE_EXCEPTION);
        }
        String newFileName = renameFile(image.getOriginalFilename(), dirName, memberId);
        return uploadImageToS3(image, newFileName);
    }

    private String renameFile(String originalFilename, String directory, Integer memberId) {
        return directory + memberId + "/" + UUID.randomUUID().toString().substring(0, 10)
            + originalFilename;
    }

    private String uploadImageToS3(MultipartFile image, String fileName)
        throws IOException {
        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(bytes.length);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucket, fileName, byteArrayInputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            log.error(e.toString());
            throw new S3Exception(S3ErrorCode.PUT_OBJECT_EXCEPTION);
        } finally {
            byteArrayInputStream.close();
            is.close();
        }

        return amazonS3.getUrl(bucket, fileName).toString();
    }
}