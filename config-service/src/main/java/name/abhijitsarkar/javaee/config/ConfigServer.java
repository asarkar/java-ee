package name.abhijitsarkar.javaee.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author Abhijit Sarkar
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigServer extends SpringBootServletInitializer {
    /* Check out the EnvironmentRepositoryConfiguration for details */
    public static void main(String[] args) {
        SpringApplication.run(ConfigServer.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ConfigServer.class);
    }
}
