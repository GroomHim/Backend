package groom.him;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GroomhimApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GroomhimApplication.class);
        app.setAdditionalProfiles("dev");
        app.run(args);
    }
}