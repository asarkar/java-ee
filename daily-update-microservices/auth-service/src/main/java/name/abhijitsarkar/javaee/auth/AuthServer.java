package name.abhijitsarkar.javaee.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Abhijit Sarkar
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class AuthServer {
    public static void main(String[] args) {
        SpringApplication.run(AuthServer.class, args);
    }

    @RequestMapping("/**")
    public Principal user(Principal user) {
        return user;
    }
}
