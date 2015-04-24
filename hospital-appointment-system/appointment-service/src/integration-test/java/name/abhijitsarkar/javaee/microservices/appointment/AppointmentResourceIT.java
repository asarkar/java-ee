package name.abhijitsarkar.javaee.microservices.appointment;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.HAL_JSON;
import static java.io.File.separator;
import static java.lang.String.join;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.StringReader;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;

@RunWith(Arquillian.class)
public class AppointmentResourceIT {
    private static final String APPOINTMENT_SERVICE_NAME = "appointment-service";
    private static final String APPOINTMENT_SERVICE_URL = join(separator,
	    "http://localhost:8080", APPOINTMENT_SERVICE_NAME, "appointment");

    private static final String DEPENDENT_SERVICE_GROUP = "name.abhijitsarkar.javaee.microservices";
    private static final String DEPENDENT_SERVICE_PACKAGING = "war";
    private static final String DEPENDENT_SERVICE_VERSION = "0.0.1-SNAPSHOT";

    private static final String AVAILABILITY_SERVICE_NAME = "availability-service";

    private static final String USER_SERVICE_NAME = "user-service";

    @Inject
    private Client client;

    private RepresentationFactory repFactory = new JsonRepresentationFactory();

    @Deployment(name = USER_SERVICE_NAME, order = 1)
    public static WebArchive createUserServiceDeployment() {
	return createDependentServiceDeployment(USER_SERVICE_NAME);
    }

    @Deployment(name = AVAILABILITY_SERVICE_NAME, order = 2)
    public static WebArchive createAvailabilityServiceDeployment() {
	return createDependentServiceDeployment(AVAILABILITY_SERVICE_NAME);
    }

    private static WebArchive createDependentServiceDeployment(String name) {
	String mvnCoordinate = join(":", DEPENDENT_SERVICE_GROUP, name,
		DEPENDENT_SERVICE_PACKAGING, DEPENDENT_SERVICE_VERSION);
	File[] service = Maven.configureResolver().workOffline()
		.withMavenCentralRepo(false).withClassPathResolution(true)
		.resolve(mvnCoordinate).withTransitivity().as(File.class);

	ZipImporter importer = ShrinkWrap.create(ZipImporter.class,
		join(".", name, DEPENDENT_SERVICE_PACKAGING));

	for (File f : service) {
	    importer = importer.importFrom(f);
	}

	return importer.as(WebArchive.class).addAsWebInfResource(
		new File("src/main/webapp", "WEB-INF/beans.xml"));
    }

    @Deployment(name = APPOINTMENT_SERVICE_NAME, order = 3)
    public static WebArchive createAppointmentServiceDeployment() {
	File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
		.importRuntimeDependencies().resolve().withTransitivity()
		.asFile();

	WebArchive appointmentService = create(
		WebArchive.class,
		join(".", APPOINTMENT_SERVICE_NAME, DEPENDENT_SERVICE_PACKAGING))
		.addPackages(true, Filters.exclude(".*Test.*"),
			AppointmentApp.class.getPackage())
		.addAsLibraries(libs)
		.addAsWebInfResource(
			new File("src/main/webapp", "WEB-INF/beans.xml"))
		.addAsWebInfResource(
			new File("src/main/webapp",
				"WEB-INF/jboss-deployment-structure.xml"))
		.addAsResource(new File("src/main/resources", "logback.xml"));

	System.out.println(appointmentService.toString(true));

	return appointmentService;
    }

    @Test
    @InSequence(1)
    @OperateOnDeployment(APPOINTMENT_SERVICE_NAME)
    public void testMakeAppointment() {
	String slotId = "1";

	String json = newAppointment(slotId);

	String repURI = getRepURI(json);

	assertEquals(join("/", APPOINTMENT_SERVICE_URL, slotId), repURI);
    }

    @Test
    @InSequence(2)
    @OperateOnDeployment(APPOINTMENT_SERVICE_NAME)
    public void testCancelAppointment() {
	/* Slot id 1 is taken by above test. */
	String slotId = "2";

	String json = newAppointment(slotId);

	String repURI = getRepURI(json);

	WebTarget wt = client.target(repURI).queryParam("patientId", "doej");
	Builder builder = wt.request();

	Response response = builder.accept(HAL_JSON).method("DELETE");
	assertEquals(OK.getStatusCode(), response.getStatus());
    }

    private String getRepURI(String json) {
	ContentRepresentation rep = repFactory.readRepresentation(HAL_JSON,
		new StringReader(json));

	return rep.getResourceLink().getHref();
    }

    private String newAppointment(String slotId) {
	WebTarget wt = client.target(APPOINTMENT_SERVICE_URL).path(slotId)
		.queryParam("patientId", "doej");
	Builder builder = wt.request();

	Response response = builder.accept(HAL_JSON).method("POST");
	assertEquals(CREATED.getStatusCode(), response.getStatus());

	return response.readEntity(String.class);
    }
}
