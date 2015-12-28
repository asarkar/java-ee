package name.abhijitsarkar.javaee.dailyupdate;

import name.abhijitsarkar.javaee.common.CommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Abhijit Sarkar
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {DailyUpdateApp.class, CommonConfig.class})
@EnableFeignClients
@EnableDiscoveryClient
public class DailyUpdateApp extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(DailyUpdateApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DailyUpdateApp.class);
    }
}
