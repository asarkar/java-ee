package name.abhijitsarkar.microservices.user;

import static java.util.Collections.unmodifiableList;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserReader {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Doctor> getDoctors() {
	return getUsers("/doctors.json", Doctor.class);
    }

    public static List<Patient> getPatients() {
	return getUsers("/patients.json", Patient.class);
    }

    private static <T extends User> List<T> getUsers(String filename,
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
