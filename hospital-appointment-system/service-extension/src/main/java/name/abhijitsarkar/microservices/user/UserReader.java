package name.abhijitsarkar.microservices.user;

import static java.util.Collections.unmodifiableList;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import name.abhijitsarkar.microservices.extension.Produces;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserReader {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Produces(Doctor.class)
    public static List<Doctor> getDoctors() {
	return getUsers("/doctors.json", Doctor.class);
    }

    @Produces(Patient.class)
    public static List<Patient> getPatients() {
	return getUsers("/patients.json", Patient.class);
    }

    private static <T extends AbstractUser> List<T> getUsers(String filename,
	    Class<T> clazz) {
	try {
	    List<T> users = mapper.readValue(UserReader.class
		    .getResource(filename), mapper.getTypeFactory()
		    .constructCollectionType(List.class, clazz));

	    return unmodifiableList(users);
	} catch (IOException e) {
	    throw new UncheckedIOException(e);
	}
    }
}
