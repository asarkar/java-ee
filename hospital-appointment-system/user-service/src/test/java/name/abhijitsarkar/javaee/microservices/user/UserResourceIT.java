package name.abhijitsarkar.javaee.microservices.user;

import static java.io.File.separator;
import static java.lang.String.join;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;
import static name.abhijitsarkar.javaee.microservices.user.domain.User.Type.Doctor;
import static name.abhijitsarkar.javaee.microservices.user.domain.User.Type.Patient;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import name.abhijitsarkar.javaee.microservices.user.domain.Doctor;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(Arquillian.class)
public class UserResourceIT {
    private static final String SERVICE_NAME = "user-service";
    private static final String SERVICE_URL = join(separator,
	    "http://localhost:8080", SERVICE_NAME, "user");
    private static final String RESOURCES_PATH = "src/main/resources";

    @Inject
    @name.abhijitsarkar.javaee.microservices.client.Client
    private Client client;

    @Deployment
    public static WebArchive createDeployment() throws FileNotFoundException {
	File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
		.importRuntimeDependencies().resolve().withTransitivity()
		.asFile();

	WebArchive app = create(WebArchive.class, SERVICE_NAME + ".war")
		.addPackages(true, Filters.exclude(".*Test.*"),
			UserApp.class.getPackage())
		.addAsWebInfResource(
			new File("src/main/webapp", "WEB-INF/beans.xml"))
		.addAsResource(new File(RESOURCES_PATH, "doctors.json"))
		.addAsResource(new File(RESOURCES_PATH, "patients.json"))
		.addAsLibraries(libs);

	System.out.println(app.toString(true));

	return app;
    }

    @Test
    public void testGetDoctors() {
	WebTarget wt = client.target(SERVICE_URL).matrixParam("type",
		Doctor.name());
	Response response = wt.request().accept(APPLICATION_JSON).get();

	assertEquals(OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetADoctor() throws JsonParseException,
	    JsonMappingException, IOException {
	WebTarget wt = client.target(SERVICE_URL).matrixParam("type",
		Doctor.name());
	Response response = wt.request().accept(APPLICATION_JSON).get();

	assertEquals(OK.getStatusCode(), response.getStatus());

	List<Doctor> doctors = new ObjectMapper().readValue(
		response.readEntity(String.class),
		new TypeReference<List<Doctor>>() {
		});

	assertTrue(doctors != null && !doctors.isEmpty());

	wt = client.target(SERVICE_URL).matrixParam("type", Doctor.name())
		.path(doctors.get(0).getUserId());
	response = wt.request().accept(APPLICATION_JSON).get();

	assertEquals(OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetPatients() {
	WebTarget wt = client.target(SERVICE_URL).matrixParam("type",
		Patient.name());
	Response response = wt.request().accept(APPLICATION_JSON).get();

	assertEquals(OK.getStatusCode(), response.getStatus());
    }
}
