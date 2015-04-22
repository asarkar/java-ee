package name.abhijitsarkar.javaee.microservices.representation;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Produces;

import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class ObjectMapperFactory {
    private static final ObjectMapper mapper = new ObjectMapper();

    @ApplicationScoped
    @Produces
    public ObjectMapper getObjectMapper() {
	return mapper;
    }
}
