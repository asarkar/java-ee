package name.abhijitsarkar.javaee.microservices.appointment;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.HAL_JSON;
import static java.io.File.separator;
import static java.lang.String.join;
import static javax.ws.rs.core.Response.Status.CREATED;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import name.abhijitsarkar.javaee.microservices.client.ClientFactory;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    private ClientFactory clientFactory;

    private Client client;

    @PostConstruct
    public void initClient() {
	client = clientFactory.newClient();
    }

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
		EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
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
		.addAsWebInfResource(EmptyAsset.INSTANCE,
			ArchivePaths.create("beans.xml"))
		.addAsWebInfResource(
			new File("src/main/webapp",
				"WEB-INF/jboss-deployment-structure.xml"))
		.addAsResource(new File("src/main/resources", "logback.xml"));

	System.out.println(appointmentService.toString(true));

	return appointmentService;
    }

    @Test
    @OperateOnDeployment(APPOINTMENT_SERVICE_NAME)
    public void testMakeAppointment() {
	WebTarget wt = client.target(APPOINTMENT_SERVICE_URL).path("1")
		.queryParam("patientId", "doej");
	Builder builder = wt.request();

	Response response = builder.accept(HAL_JSON).method("POST");
	assertEquals(CREATED.getStatusCode(), response.getStatus());
    }
}
