package name.abhijitsarkar.javaee.microservices.client;

import javax.ws.rs.client.Client;

public interface ClientFactory {
    Client newClient();
}
