package name.abhijitsarkar.javaee.microservices.user.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import name.abhijitsarkar.javaee.microservices.representation.ObjectMapperFactory;
import name.abhijitsarkar.javaee.microservices.user.domain.User;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserServiceTest {
    private ObjectMapperFactory mapperFactory = mock(ObjectMapperFactory.class);
    private UserService service = new UserService();

    @Before
    public void beforeAllTests() {
	when(mapperFactory.getObjectMapper()).thenReturn(new ObjectMapper());
	service.setMapperFactory(mapperFactory);
    }

    @Test
    public void testDoctors() {
	assertTrue(isUserPresent(service.getDoctors(), "appleseedj"));
    }

    @Test
    public void testPatients() {
	assertTrue(isUserPresent(service.getPatients(), "doej"));
    }

    private boolean isUserPresent(List<? extends User> users, String userId) {
	return users.stream().anyMatch(user -> user.getUserId().equals(userId));
    }
}
