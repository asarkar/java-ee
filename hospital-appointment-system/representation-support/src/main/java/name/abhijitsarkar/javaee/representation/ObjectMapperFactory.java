package name.abhijitsarkar.javaee.representation;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

@Dependent
public class ObjectMapperFactory {
    private static final ObjectMapper mapper = new ObjectMapper();

    /*
     * The ObjectMapper has final methods and hence cannot be proxied.
     * https://docs.jboss.org/weld/reference/latest/en-US/html/scopescontexts.html#_the_singleton_pseudo_scope
     */
    @Produces
    @Singleton
    public ObjectMapper getObjectMapper() {
	return mapper;
    }
}
