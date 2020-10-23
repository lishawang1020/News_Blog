package NowNewsBlog;

import NowNewsBlog.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfig.class)
public class NowNewsBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(NowNewsBlogApplication.class, args);
    }

}
