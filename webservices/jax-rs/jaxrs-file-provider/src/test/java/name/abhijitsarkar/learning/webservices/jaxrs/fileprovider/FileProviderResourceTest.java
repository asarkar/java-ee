package name.abhijitsarkar.learning.webservices.jaxrs.fileprovider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class FileProviderResourceTest extends JerseyTest {
	@Override
	protected Application configure() {
		return new AppConfig();
	}

	@Test
	public void testWhenFileExists() throws URISyntaxException {
		final URI uri = this.getClass().getResource("/test.txt").toURI();

		final Response response = target("file").queryParam("uri", uri)
				.request().get();

		assertEquals(OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testWhenFileDoesNotExist() {
		final Response response = target("file").queryParam("uri", "/blah")
				.request().get();

		assertEquals(NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testWhenNoURIParamIsSupplied() {
		final Response response = target("file").request().get();

		assertEquals(BAD_REQUEST.getStatusCode(), response.getStatus());
	}
}
