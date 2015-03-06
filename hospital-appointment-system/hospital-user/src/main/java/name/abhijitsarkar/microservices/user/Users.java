package name.abhijitsarkar.microservices.user;

import static java.util.Collections.unmodifiableList;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

@ApplicationScoped
public class Users {
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public Users() {
	/* Register optional Jackson module to support Java 8 datetime types. */
	mapper.registerModule(new JSR310Module());
    }
    
    public ObjectMapper getMapper() {
	return mapper;
    }
    
    // @Produces
    // @Doctors
    public List<Doctor> getDoctors() {
	return getUsers("/doctors.json", Doctor.class);
    }

    // @Produces
    // @Patients
    public List<Patient> getPatients() {
	return getUsers("/patients.json", Patient.class);
    }

    private static <T extends User> List<T> getUsers(String filename,
	    Class<T> clazz) {
	try {
	    List<T> users = mapper.readValue(
		    Users.class.getResource(filename),
		    mapper.getTypeFactory().constructCollectionType(List.class,
			    clazz));

	    return unmodifiableList(users);
	} catch (IOException e) {
	    throw new UncheckedIOException(e);
	}
    }
}
