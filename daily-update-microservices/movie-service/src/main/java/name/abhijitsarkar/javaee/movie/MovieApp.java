package name.abhijitsarkar.javaee.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author Abhijit Sarkar
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class MovieApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(MovieApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MovieApp.class);
    }
}
