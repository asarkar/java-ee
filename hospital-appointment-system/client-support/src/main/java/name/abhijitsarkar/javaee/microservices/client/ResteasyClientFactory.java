package name.abhijitsarkar.javaee.microservices.client;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

@ApplicationScoped
public class ResteasyClientFactory implements ClientFactory {
    private Client client;

    @PostConstruct
    public void initClient() {
	client = new ResteasyClientBuilder().connectionPoolSize(10)
		.maxPooledPerRoute(5).build();
    }

    @PreDestroy
    public void disposeClient() {
	client.close();
    }

    @Override
    @Produces
    @ApplicationScoped
    @name.abhijitsarkar.javaee.microservices.client.Client
    public Client newClient() {
	return client;
    }
}
