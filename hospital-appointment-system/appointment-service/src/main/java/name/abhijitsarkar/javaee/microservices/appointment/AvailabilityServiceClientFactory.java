package name.abhijitsarkar.javaee.microservices.appointment;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.HAL_JSON;
import static java.io.File.separator;
import static java.lang.String.join;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;

import name.abhijitsarkar.javaee.microservices.client.ClientFactory;

@ApplicationScoped
public class AvailabilityServiceClientFactory {
    private static final String SERVICE_NAME = "availability-service";
    private static final String SERVICE_URL = join(separator,
	    "http://localhost:8080", SERVICE_NAME, "slot");

    @Inject
    private ClientFactory clientFactory;

    private Client client;

    @PostConstruct
    public void initClient() {
	client = clientFactory.newClient();
    }

    public Builder newSlotTargetBuilder(int id) {
	return client.target(SERVICE_URL).path(String.valueOf(id)).request()
		.accept(HAL_JSON);
    }

    public Builder newTargetBuilder(String uri) {
	return client.target(uri).request().accept(HAL_JSON);
    }
}
