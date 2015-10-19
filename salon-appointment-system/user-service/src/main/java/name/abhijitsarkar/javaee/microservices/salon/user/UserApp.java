package name.abhijitsarkar.javaee.microservices.salon.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@EnableDiscoveryClient
@Profile("!test")
public class UserApp {
	public static void main(String[] args) {
		System.setProperty("spring.config.location", "classpath:/user-service.yml, classpath:/salon-common.yml");
		System.setProperty("spring.cloud.bootstrap.location", "classpath:/user-service-registration.yml");
		
		SpringApplication.run(UserApp.class, args);
	}
}
