package name.abhijitsarkar.javaee.microservices.user.service;

import static java.util.Collections.unmodifiableList;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;

import name.abhijitsarkar.javaee.microservices.representation.ObjectMapperFactory;
import name.abhijitsarkar.javaee.microservices.user.domain.Doctor;
import name.abhijitsarkar.javaee.microservices.user.domain.Doctors;
import name.abhijitsarkar.javaee.microservices.user.domain.Patient;
import name.abhijitsarkar.javaee.microservices.user.domain.Patients;
import name.abhijitsarkar.javaee.microservices.user.domain.User;

@ApplicationScoped
public class UserService {
    @Inject
    private ObjectMapperFactory mapperFactory;

    void setMapperFactory(ObjectMapperFactory mapperFactory) {
	this.mapperFactory = mapperFactory;
    }

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

    private <T extends User> List<T> getUsers(String filename, Class<T> clazz) {
	try {
	    List<T> users = mapperFactory.getObjectMapper().readValue(
		    UserService.class.getResource(filename),
		    mapperFactory.getObjectMapper().getTypeFactory()
			    .constructCollectionType(List.class, clazz));

	    return unmodifiableList(users);
	} catch (IOException e) {
	    throw new UncheckedIOException(e);
	}
    }
}
