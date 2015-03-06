package name.abhijitsarkar.microservices.user;

import static java.util.Collections.unmodifiableList;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import javax.ws.rs.Produces;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Users {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Produces
    @Doctors
    public List<Doctor> getDoctors() {
	return getUsers("/doctors.json", Doctor.class);
    }

    @Produces
    @Patients
    public List<Patient> getPatients() {
	return getUsers("/patients.json", Patient.class);
    }

    private static <T extends User> List<T> getUsers(String filename,
	    Class<T> clazz) {
	try {
	    List<T> users = mapper.readValue(Users.class
		    .getResource(filename), mapper.getTypeFactory()
		    .constructCollectionType(List.class, clazz));

	    return unmodifiableList(users);
	} catch (IOException e) {
	    throw new UncheckedIOException(e);
	}
    }
}
