package name.abhijitsarkar.microservices.user;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.IntStream;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

public class UserResourceTest {
    private Users users;
    private UserResource userResource;

    @Before
    public void init() {
	userResource = new UserResource();

	List<Doctor> doctors = IntStream.range(1, 3)
		.mapToObj(i -> String.format("doctor%d", i))
		.map(docId -> new Doctor(docId, "Good", "Doctor"))
		.collect(toList());

	List<Patient> patients = IntStream.range(1, 3)
		.mapToObj(i -> String.format("patient%d", i))
		.map(patientId -> new Patient(patientId, "Sick", "Patient"))
		.collect(toList());

	users = mock(Users.class);

	when(users.getDoctors()).thenReturn(doctors);
	when(users.getPatients()).thenReturn(patients);

	userResource.setUsers(users);
	userResource.initUsers();
    }

    @Test
    public void testGetDoctor() {
	Response response = userResource.getUser("doctor1", "Doctor");

	assertEquals(OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetPatient() {
	Response response = userResource.getUser("patient1", "Patient");

	assertEquals(OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetPatientWithWrongId() {
	Response response = userResource.getUser("patient10", "Patient");

	assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetNonExistentUser() {
	Response response = userResource.getUser("patient1", "Junk");

	assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
