package name.abhijitsarkar.javaee.microservices.availability.service;

import static java.io.File.separator;
import static java.lang.String.join;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static name.abhijitsarkar.javaee.microservices.user.domain.User.Type.Doctor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;

import name.abhijitsarkar.javaee.microservices.client.ClientFactory;

//@ApplicationScoped
public class UserServiceClientFactory {
//    private static final String SERVICE_NAME = "user-service";
//    private static final String SERVICE_URL = join(separator,
//	    "http://localhost:8080", SERVICE_NAME, "user");
//
//    @Inject
//    private ClientFactory clientFactory;
//
//    private Client client;
//
//    @PostConstruct
//    public void initClient() {
//	client = clientFactory.newClient();
//    }
//
//    public Builder newDoctorsTargetBuilder() {
//	return client.target(SERVICE_URL).matrixParam("type", Doctor.name())
//		.request().accept(APPLICATION_JSON);
//    }
}
