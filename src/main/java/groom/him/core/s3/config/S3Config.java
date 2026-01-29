package groom.him.core.s3.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import groom.him.core.s3.service.S3Service;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class S3Config {
    @Bean
    public AmazonS3Client amazonS3Client() {
        return (AmazonS3Client) AmazonS3ClientBuilder
            .standard()
            .build();
    }

    @Bean(name = "uploadExecutor")
    public Executor uploadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("S3-UPLOAD-");
        executor.initialize();
        return executor;
    }

    @Bean
    public S3Service s3Service(
        AmazonS3 amazonS3,
        Executor uploadExecutor,
        @Value("${app.s3.bucket}") String bucket
    ) {
        return new S3Service(amazonS3, uploadExecutor, bucket);
    }
}