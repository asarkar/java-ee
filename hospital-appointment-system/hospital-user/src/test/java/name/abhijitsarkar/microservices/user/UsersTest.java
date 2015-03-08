package name.abhijitsarkar.microservices.user;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import name.abhijitsarkar.microservices.representation.ObjectMapperFactory;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UsersTest {
    private Users users = new Users();
    private ObjectMapperFactory mapperFactory = mock(ObjectMapperFactory.class);

    public UsersTest() {
	when(mapperFactory.getObjectMapper()).thenReturn(new ObjectMapper());

	users.setMapperFactory(mapperFactory);
    }

    @Test
    public void testDoctors() {
	assertTrue(isUserPresent(users.getDoctors(), "appleseedj"));
    }

    @Test
    public void testPatients() {
	assertTrue(isUserPresent(users.getPatients(), "doej"));
    }

    private boolean isUserPresent(List<? extends User> users, String userId) {
	return users.stream().anyMatch(user -> user.getUserId().equals(userId));
    }
}
