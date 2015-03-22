package name.abhijitsarkar.microservices.availability;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.HAL_JSON;
import static java.io.File.separator;
import static java.lang.String.join;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.api.Link;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;

@RunWith(Arquillian.class)
public class AvailabilityResourceIntegrationTest {
    private static final String SERVICE_NAME = "availability-service";
    private static final String SERVICE_URL = join(separator,
	    "http://localhost:8080", SERVICE_NAME, "slot");
    private static final String WEB_APP_PATH = "src/main/webapp";
    private static final String HOSPITAL_USER_MVN_COORD = "name.abhijitsarkar.microservices:hospital-user";

    private Client client;

    private RepresentationFactory repFactory = new JsonRepresentationFactory();

    // https://github.com/shrinkwrap/resolver
    @Deployment
    public static WebArchive createDeployment() throws FileNotFoundException {
	File[] hospitalUser = Maven.configureResolver().workOffline()
		.withMavenCentralRepo(false).withClassPathResolution(true)
		.loadPomFromFile(new File("pom.xml"))
		.resolve(HOSPITAL_USER_MVN_COORD).withTransitivity().asFile();

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
    public void testGetSlotsWithDefaultDate() {
	WebTarget wt = client.target(SERVICE_URL);
	Builder builder = wt.request();

	Response response = builder.accept(HAL_JSON).get();
	assertEquals(OK.getStatusCode(), response.getStatus());

	String json = response.readEntity(String.class);

	ContentRepresentation rep = repFactory.readRepresentation(HAL_JSON,
		new StringReader(json));

	assertNotNull(rep.getContent());
	assertFalse(rep.getContent().isEmpty());

	List<Link> links = rep.getLinks();
	assertNotNull(links);
	assertFalse(links.isEmpty());

	List<String> slots = links.stream()
		.filter(link -> "item".equals(link.getRel())).map(Link::getRel)
		.collect(toList());

	assertNotNull(slots);
	assertFalse(slots.isEmpty());
    }

    @Test
    public void testGetSlotsForYesterday() {
	WebTarget wt = client.target(SERVICE_URL);
	Builder builder = wt.queryParam("date",
		ISO_LOCAL_DATE.format(LocalDate.now().minusDays(1))).request();

	Response response = builder.accept(HAL_JSON).get();

	assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetSlotsForASaturday() {
	WebTarget wt = client.target(SERVICE_URL);
	Builder builder = wt.queryParam(
		"date",
		ISO_LOCAL_DATE.format(LocalDate.now()
			.with(nextOrSame(SATURDAY)))).request();

	Response response = builder.accept(HAL_JSON).get();
	assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetFirstSlotForNextMondayUsingLink() {
	String json = getSlotsForNextMonday();

	ContentRepresentation rep = repFactory.readRepresentation(HAL_JSON,
		new StringReader(json));

	assertNotNull(rep.getContent());
	assertFalse(rep.getContent().isEmpty());

	List<Link> links = rep.getLinks();
	assertNotNull(links);
	assertFalse(links.isEmpty());

	Optional<Link> start = links.stream()
		.filter(link -> "start".equals(link.getRel())).findAny();

	assertTrue(start.isPresent());

	Builder builder = client.target(start.get().getHref()).request();

	Response response = builder.accept(HAL_JSON).get();

	assertEquals(OK.getStatusCode(), response.getStatus());
    }

    private String getSlotsForNextMonday() {
	Client c = ClientBuilder.newClient();

	WebTarget wt = c.target(SERVICE_URL);
	Builder builder = wt
		.queryParam(
			"date",
			ISO_LOCAL_DATE.format(LocalDate.now().with(
				nextOrSame(MONDAY)))).request();

	Response response = builder.accept(HAL_JSON).get();
	assertEquals(OK.getStatusCode(), response.getStatus());

	String slots = response.readEntity(String.class);

	c.close();

	return slots;
    }
}
