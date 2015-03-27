package name.abhijitsarkar.javaee.microservices.appointment;

import static java.io.File.separator;
import static java.lang.String.join;
import static javax.ws.rs.core.Response.Status.CREATED;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import name.abhijitsarkar.javaee.microservices.appointment.AppointmentApp;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AppointmentResourceIT {
    private static final String APPOINTMENT_SERVICE_NAME = "appointment-service";
    private static final String APPOINTMENT_SERVICE_URL = join(separator,
	    "http://localhost:8080", APPOINTMENT_SERVICE_NAME, "appointment");
    private static final String AVAILABILITY_SERVICE_NAME = "availability-service";
    private static final String AVAILABILITY_SERVICE_MVN_COORD = "name.abhijitsarkar.javaee.microservices:"
	    + AVAILABILITY_SERVICE_NAME + ":war:0.0.1-SNAPSHOT";

    private Client client;

    @Deployment(name = AVAILABILITY_SERVICE_NAME, order = 1)
    public static WebArchive createAvailabilityServiceDeployment() {
	WebArchive availabilityService = Maven.configureResolver()
		.workOffline().withMavenCentralRepo(false)
		.withClassPathResolution(true)
		.resolve(AVAILABILITY_SERVICE_MVN_COORD).withoutTransitivity()
		.asSingle(WebArchive.class);

	System.out.println(availabilityService.toString(true));

	return availabilityService;
    }

    @Deployment(name = APPOINTMENT_SERVICE_NAME, order = 2)
    public static WebArchive createAppointmentServiceDeployment()
	    throws FileNotFoundException {
	WebArchive appointmentService = create(WebArchive.class,
		APPOINTMENT_SERVICE_NAME + ".war").addPackages(true,
		Filters.exclude(".*Test.*"), AppointmentApp.class.getPackage())
		.addAsWebInfResource(EmptyAsset.INSTANCE,
			ArchivePaths.create("beans.xml"));

	System.out.println(appointmentService.toString(true));

	return appointmentService;
    }

    @Before
    public void initClient() {
	client = ClientBuilder.newClient();
    }

    @After
    public void destroyClient() {
	client.close();
    }

    @Test
    @OperateOnDeployment(APPOINTMENT_SERVICE_NAME)
    public void testMakeAppointment() {
	WebTarget wt = client.target(APPOINTMENT_SERVICE_URL).path(
		"1;patientId=1");
	Builder builder = wt.request();

	Response response = builder.accept(MediaType.APPLICATION_JSON).method(
		"POST");
	assertEquals(CREATED.getStatusCode(), response.getStatus());
    }
}
