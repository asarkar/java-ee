package name.abhijitsarkar.microservices.user;

import static java.util.Collections.unmodifiableList;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import name.abhijitsarkar.microservices.representation.ObjectMapperFactory;

@ApplicationScoped
public class Users {
    @Inject
    private ObjectMapperFactory mapperFactory;

    void setMapperFactory(ObjectMapperFactory mapperFactory) {
	this.mapperFactory = mapperFactory;
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

    private <T extends User> List<T> getUsers(String filename, Class<T> clazz) {
	try {
	    List<T> users = mapperFactory.getObjectMapper().readValue(
		    Users.class.getResource(filename),
		    mapperFactory.getObjectMapper().getTypeFactory()
			    .constructCollectionType(List.class, clazz));

	    return unmodifiableList(users);
	} catch (IOException e) {
	    throw new UncheckedIOException(e);
	}
    }
}
