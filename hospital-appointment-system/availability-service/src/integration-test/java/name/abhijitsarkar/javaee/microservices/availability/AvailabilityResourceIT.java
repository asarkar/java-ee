package name.abhijitsarkar.javaee.microservices.availability;

import static com.theoryinpractise.halbuilder.api.RepresentationFactory.HAL_JSON;
import static java.io.File.separator;
import static java.lang.String.join;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static javax.ws.rs.HttpMethod.PUT;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.theoryinpractise.halbuilder.api.ContentRepresentation;
import com.theoryinpractise.halbuilder.api.Link;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.json.JsonRepresentationFactory;

@RunWith(Arquillian.class)
public class AvailabilityResourceIT {
    private static final String AVAILABILITY_SERVICE_NAME = "availability-service";
    private static final String AVAILABILITY_SERVICE_URL = join(separator,
	    "http://localhost:8080", AVAILABILITY_SERVICE_NAME, "slot");
    private static final String USER_SERVICE_GROUP = "name.abhijitsarkar.javaee.microservices";
    private static final String USER_SERVICE_NAME = "user-service";
    private static final String USER_SERVICE_PACKAGING = "war";
    private static final String USER_SERVICE_VERSION = "0.0.1-SNAPSHOT";

    private RepresentationFactory repFactory = new JsonRepresentationFactory();

    @Inject
    private Client client;

    @Deployment(name = USER_SERVICE_NAME, order = 1)
    public static WebArchive createUserServiceDeployment() {
	String mvnCoordinate = join(":", USER_SERVICE_GROUP, USER_SERVICE_NAME,
		USER_SERVICE_PACKAGING, USER_SERVICE_VERSION);
	File[] service = Maven.configureResolver().workOffline()
		.withMavenCentralRepo(false).withClassPathResolution(true)
		.resolve(mvnCoordinate).withTransitivity().asFile();

	ZipImporter importer = ShrinkWrap.create(ZipImporter.class,
		join(".", USER_SERVICE_NAME, USER_SERVICE_PACKAGING));

	for (File f : service) {
	    importer = importer.importFrom(f);
	}

	return importer.as(WebArchive.class).addAsWebInfResource(
		new File("src/main/webapp", "WEB-INF/beans.xml"));
    }

    // https://github.com/shrinkwrap/resolver
    @Deployment(name = AVAILABILITY_SERVICE_NAME, order = 2)
    public static WebArchive createAvailabilityServiceDeployment()
	    throws FileNotFoundException {
	File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
		.importRuntimeDependencies().resolve().withTransitivity()
		.asFile();
	WebArchive app = create(WebArchive.class,
		AVAILABILITY_SERVICE_NAME + ".war")
		.addPackages(true, Filters.exclude(".*Test.*"),
			AvailabilityApp.class.getPackage())
		.addAsWebInfResource(
			new File("src/main/webapp", "WEB-INF/beans.xml"))
		.addAsLibraries(libs);

	System.out.println(app.toString(true));

	return app;
    }

    @Test
    @OperateOnDeployment(AVAILABILITY_SERVICE_NAME)
    public void testGetSlotsWithDefaultDate() {
	WebTarget wt = client.target(AVAILABILITY_SERVICE_URL);
	Builder builder = wt.request();

	Response response = builder.accept(HAL_JSON).get();
	assertEquals(OK.getStatusCode(), response.getStatus());

	String json = response.readEntity(String.class);

	ContentRepresentation rep = repFactory.readRepresentation(HAL_JSON,
		new StringReader(json));

	assertNotNull(rep.getContent());
	assertFalse(rep.getContent().isEmpty());

	List<Link> links = rep.getLinksByRel("item");
	assertNotNull(links);
	assertFalse(links.isEmpty());
    }

    @Test
    @OperateOnDeployment(AVAILABILITY_SERVICE_NAME)
    public void testGetSlotsForYesterday() {
	WebTarget wt = client.target(AVAILABILITY_SERVICE_URL);
	Builder builder = wt.queryParam("date",
		ISO_LOCAL_DATE.format(LocalDate.now().minusDays(1))).request();

	Response response = builder.accept(HAL_JSON).get();

	assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    @OperateOnDeployment(AVAILABILITY_SERVICE_NAME)
    public void testGetSlotsForASaturday() {
	WebTarget wt = client.target(AVAILABILITY_SERVICE_URL);
	Builder builder = wt.queryParam(
		"date",
		ISO_LOCAL_DATE.format(LocalDate.now()
			.with(nextOrSame(SATURDAY)))).request();

	Response response = builder.accept(HAL_JSON).get();

	assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    @OperateOnDeployment(AVAILABILITY_SERVICE_NAME)
    public void testGetFirstSlotForNextMondayUsingLink() {
	String json = getSlotsForNextMonday();

	ContentRepresentation rep = repFactory.readRepresentation(HAL_JSON,
		new StringReader(json));

	assertNotNull(rep.getContent());
	assertFalse(rep.getContent().isEmpty());

	Link start = rep.getLinkByRel("start");
	assertNotNull(start);

	Builder builder = client.target(start.getHref()).request();

	Response response = builder.accept(HAL_JSON).get();

	assertEquals(OK.getStatusCode(), response.getStatus());
    }

    private String getSlotsForNextMonday() {
	WebTarget wt = client.target(AVAILABILITY_SERVICE_URL);
	Builder builder = wt
		.queryParam(
			"date",
			ISO_LOCAL_DATE.format(LocalDate.now().with(
				nextOrSame(MONDAY)))).request();

	Response response = builder.accept(HAL_JSON).get();
	assertEquals(OK.getStatusCode(), response.getStatus());

	String slots = response.readEntity(String.class);

	return slots;
    }

    @Test
    @OperateOnDeployment(AVAILABILITY_SERVICE_NAME)
    public void testReserveAndRelinquishSlot() {
	/* Get all slots for next Monday */
	String json = getSlotsForNextMonday();

	ContentRepresentation rep = repFactory.readRepresentation(HAL_JSON,
		new StringReader(json));

	Link start = rep.getLinkByRel("start");
	assertNotNull(start);

	String oldStartUri = start.getHref();

	String reservationUri = findEditUriWithTitleFromRepWithUri(rep,
		"reserve", oldStartUri);

	/* Reserve the 1st slot */
	Builder builder = client.target(reservationUri).request();

	Response response = builder.accept(HAL_JSON).method(PUT);

	assertEquals(OK.getStatusCode(), response.getStatus());

	/*
	 * Get all slots for next Monday and verify that it doesn't contain the
	 * reserved slot
	 */
	json = getSlotsForNextMonday();

	rep = repFactory.readRepresentation(HAL_JSON, new StringReader(json));

	start = rep.getLinkByRel("start");

	assertFalse(oldStartUri.equals(start.getHref()));
    }

    private String findEditUriWithTitleFromRepWithUri(
	    ContentRepresentation rep, String title, String uri) {
	return rep.getResourceMap().get("slots").stream()
		.filter(r -> r.getLinkByRel("self").getHref().equals(uri))
		.findAny().get().getLinksByRel("edit").stream()
		.filter(l -> l.getTitle().equals(title)).findAny()
		.map(l -> l.getHref()).get();
    }
}
