package name.abhijitsarkar.microservices.availability;

import static java.io.File.separator;
import static java.lang.String.join;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AvailabilityResourceIntegrationTest {
    private static final String SERVICE_NAME = "availability-service";
    private static final String SERVICE_URL = join(separator,
	    "http://localhost:8080", SERVICE_NAME, "slot");
    private static final String WEB_APP_PATH = "src/main/webapp";
    private static final String HOSPITAL_USER_MVN_COORD = "name.abhijitsarkar.microservices:hospital-user";

    private Client client;

    // https://github.com/shrinkwrap/resolver
    @Deployment(testable = false)
    public static WebArchive createDeployment() throws FileNotFoundException {
	File[] hospitalUser = Maven.configureResolver().workOffline()
		.withMavenCentralRepo(false).withClassPathResolution(true)
		.loadPomFromFile(new File("pom.xml"))
		/* Transitive dependencies mess up the deployment. */
		.resolve(HOSPITAL_USER_MVN_COORD).withoutTransitivity()
		.asFile();

	WebArchive app = create(WebArchive.class, SERVICE_NAME + ".war")
		.addPackages(true, Filters.exclude(".*Test.*"),
			AvailabilityApp.class.getPackage())
		.addAsWebInfResource(
			new File(WEB_APP_PATH, "WEB-INF/beans.xml"))
		.addAsLibraries(hospitalUser);

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
