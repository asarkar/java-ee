package name.abhijitsarkar.javaee.microservices.user;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.IntStream;

import javax.ws.rs.core.Response;

import name.abhijitsarkar.javaee.microservices.user.UserResource;
import name.abhijitsarkar.javaee.microservices.user.domain.Doctor;
import name.abhijitsarkar.javaee.microservices.user.domain.Patient;
import name.abhijitsarkar.javaee.microservices.user.service.UserService;

import org.junit.Before;
import org.junit.Test;

public class UserResourceTest {
    private UserService userService;
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

	userService = mock(UserService.class);

	when(userService.getDoctors()).thenReturn(doctors);
	when(userService.getPatients()).thenReturn(patients);

	userResource.setUsers(userService);
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
