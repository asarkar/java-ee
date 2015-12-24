package name.abhijitsarkar.javaee.user.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import name.abhijitsarkar.javaee.user.domain.User;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserServiceTest {
    private UserService service = new UserService();

    @Before
    public void beforeAllTests() {
	service.setObjectMapper(new ObjectMapper());
	service.initUsers();
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
