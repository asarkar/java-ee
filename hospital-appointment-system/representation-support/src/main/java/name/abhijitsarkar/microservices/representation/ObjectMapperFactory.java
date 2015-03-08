package name.abhijitsarkar.microservices.representation;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class ObjectMapperFactory {
    private static final ObjectMapper mapper = new ObjectMapper();

    public ObjectMapper getObjectMapper() {
	return mapper;
    }
}
