package name.abhijitsarkar.microservices.user;

import static name.abhijitsarkar.microservices.user.UserReader.getDoctors;
import static name.abhijitsarkar.microservices.user.UserReader.getPatients;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class UserReaderTest {
    @Test
    public void testDoctors() {
	assertTrue(isUserPresent(getDoctors(), "appleseedj"));
    }

    @Test
    public void testPatients() {
	assertTrue(isUserPresent(getPatients(), "doej"));
    }

    private boolean isUserPresent(List<? extends User> users, String userId) {
	return users.stream().anyMatch(user -> user.getUserId().equals(userId));
    }
}
