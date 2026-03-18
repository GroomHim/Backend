package groom.him.performance.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import groom.him.core.s3.service.S3Service;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Profile("!test")
@Configuration
@PropertySource("classpath:application-test.properties")
public class S3TestConfig {

    @Bean
    public AmazonS3 amazonS3(
        @Value("${cloud.aws.region.static}") String region
    ) {
        System.out.println("[DEBUG] S3 region = " + region);
        return AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .build();
    }

    @Bean(name = "uploadExecutor")
    public Executor uploadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("S3-TEST-");
        executor.initialize();
        return executor;
    }

    @Bean
    public S3Service s3Service(
        AmazonS3 amazonS3,
        Executor uploadExecutor,
        @Value("${app.s3.bucket}") String bucket
    ) {
        System.out.println("[DEBUG] S3 bucket = " + bucket);
        return new S3Service(amazonS3, uploadExecutor, bucket);
    }
}