package name.abhijitsarkar.microservices.appointment;

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

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AppointmentResourceIntegrationTest {
    private static final String SERVICE_NAME = "appointment-service";
    private static final String SERVICE_URL = join(separator,
	    "http://localhost:8080", SERVICE_NAME, "appointment");

    private Client client;

    @Deployment
    public static WebArchive createDeployment() throws FileNotFoundException {
	WebArchive app = create(WebArchive.class, SERVICE_NAME + ".war")
		.addPackages(true, Filters.exclude(".*Test.*"),
			AppointmentApp.class.getPackage()).addAsWebInfResource(
			EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));

	System.out.println(app.toString(true));

	return app;
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
    public void testMakeAppointment() {
	WebTarget wt = client.target(SERVICE_URL).path("1;patientId=1");
	Builder builder = wt.request();

	Response response = builder.accept(MediaType.APPLICATION_JSON).method(
		"POST");
	assertEquals(CREATED.getStatusCode(), response.getStatus());
    }
}
