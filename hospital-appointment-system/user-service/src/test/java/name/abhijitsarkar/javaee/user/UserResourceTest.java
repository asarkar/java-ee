package name.abhijitsarkar.javaee.user;

import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static name.abhijitsarkar.javaee.user.domain.User.Type.Doctor;
import static name.abhijitsarkar.javaee.user.domain.User.Type.Patient;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.IntStream;

import javax.ws.rs.core.Response;

import name.abhijitsarkar.javaee.user.domain.Doctor;
import name.abhijitsarkar.javaee.user.domain.Patient;
import name.abhijitsarkar.javaee.user.service.UserService;

import org.junit.Before;
import org.junit.Test;

public class UserResourceTest {
    private UserService userService;
    private UserResource userResource;

    @Before
    @SuppressWarnings("rawtypes")
    public void init() {
	userResource = new UserResource();

	List doctors = IntStream.range(1, 3)
		.mapToObj(i -> String.format("doctor%d", i))
		.map(docId -> new Doctor(docId, "Good", "Doctor"))
		.collect(toList());
	List patients = IntStream.range(1, 3)
		.mapToObj(i -> String.format("patient%d", i))
		.map(patientId -> new Patient(patientId, "Sick", "Patient"))
		.collect(toList());

	userService = mock(UserService.class);

	when(userService.getUsersByType(Doctor.name())).thenReturn(doctors);
	when(userService.getUsersByType(Patient.name())).thenReturn(patients);

	userResource.setUserService(userService);
    }

    @Test
    public void testGetDoctor() {
	Response response = userResource.getUser("doctor1", Doctor.name());

	assertEquals(OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetPatient() {
	Response response = userResource.getUser("patient1", Patient.name());

	assertEquals(OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetPatientWithWrongId() {
	Response response = userResource.getUser("patient10", Patient.name());

	assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetNonExistentUser() {
	Response response = userResource.getUser("patient1", "Junk");

	assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
