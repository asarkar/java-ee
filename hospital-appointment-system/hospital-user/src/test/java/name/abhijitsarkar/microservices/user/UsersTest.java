package name.abhijitsarkar.microservices.user;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class UsersTest {
    private Users users = new Users();

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
