package groom.him.service.s3;

import groom.him.config.S3TestConfig;
import groom.him.core.s3.service.S3Service;
import groom.him.domain.product.models.enums.ImgType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = S3TestConfig.class)
class S3UploadPerformanceTest {

    @Autowired
    private S3Service s3Service;

    private List<MultipartFile> testImages;

    private static final int FILE_COUNT = 10;
    private static final int FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final int REPEAT_COUNT = 5;

    @BeforeEach
    void setUp() {
        testImages = createFiles();
    }

    @Test
    void upload_sequential_average_performance_test() {
        long totalTime = 0;

        s3Service.uploadImagesSequential(testImages, 1, ImgType.MAIN, "product/warmup");

        for (int i = 1; i <= REPEAT_COUNT; i++) {
            long start = System.currentTimeMillis();

            s3Service.uploadImagesSequential(testImages, 1, ImgType.MAIN, "product/sequential");

            long elapsed = System.currentTimeMillis() - start;
            totalTime += elapsed;

            System.out.println("[TEST][직렬 업로드] " + i + "회차 = " + elapsed + "ms");
        }

        System.out.println("[TEST][직렬 업로드][평균] " + (totalTime / REPEAT_COUNT) + "ms");
    }

    @Test
    void upload_parallel_average_performance_test() {
        long totalTime = 0;

        s3Service.uploadImagesParallel(testImages, 1, ImgType.MAIN, "product/warmup");

        for (int i = 1; i <= REPEAT_COUNT; i++) {
            long start = System.currentTimeMillis();

            s3Service.uploadImagesParallel(testImages, 1, ImgType.MAIN, "product/parallel");

            long elapsed = System.currentTimeMillis() - start;
            totalTime += elapsed;

            System.out.println("[TEST][병렬 업로드] " + i + "회차 = " + elapsed + "ms");
        }

        System.out.println("[TEST][병렬 업로드][평균] " + (totalTime / REPEAT_COUNT) + "ms");
    }

    private List<MultipartFile> createFiles() {
        List<MultipartFile> list = new ArrayList<>();
        for (int i = 0; i < FILE_COUNT; i++) {
            byte[] content = new byte[FILE_SIZE];
            new Random().nextBytes(content);
            list.add(new MockMultipartFile(
                "file",
                "test-" + i + ".jpg",
                "image/jpeg",
                content
            ));
        }
        return list;
    }
}