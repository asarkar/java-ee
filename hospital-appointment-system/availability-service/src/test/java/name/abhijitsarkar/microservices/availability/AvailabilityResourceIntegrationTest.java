package name.abhijitsarkar.microservices.availability;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AvailabilityResourceIntegrationTest {
    private static final String SERVICE_NAME = "availability-service";
    private static final String SERVICE_URL = "http://localhost:8080/"
	    + SERVICE_NAME + "/slot";

    private Client client;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
	WebArchive app = create(WebArchive.class, SERVICE_NAME + ".war")
		.addPackages(true, AvailabilityApp.class.getPackage());

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
    public void testGetSlots() {
	client = ClientBuilder.newClient();

	WebTarget wt = client.target(SERVICE_URL);
	Builder builder = wt.queryParam("date", "2015-03-04").request();

	GenericType<List<Slot>> slots = new GenericType<List<Slot>>() {
	};

	List<Slot> response = builder.accept(APPLICATION_JSON).get(slots);

	assertNotNull(response);
    }
}
