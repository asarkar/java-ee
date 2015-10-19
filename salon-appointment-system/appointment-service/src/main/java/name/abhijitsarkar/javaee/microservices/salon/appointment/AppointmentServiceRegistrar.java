package name.abhijitsarkar.javaee.microservices.salon.appointment;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableDiscoveryClient
@Profile("!test")
public class AppointmentServiceRegistrar {

}
