package name.abhijitsarkar.javaee.client;

import javax.ws.rs.client.Client;

public interface ClientFactory {
    Client newClient();
}
