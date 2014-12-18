package name.abhijitsarkar.learning.webservices.jaxrs.fileprovider;

import static java.net.URI.create;
import static java.nio.file.Paths.get;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.slf4j.LoggerFactory.getLogger;

import java.net.URI;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;

@javax.ws.rs.Path("/file")
public class FileProviderResource {
	public static final Logger LOGGER = getLogger(FileProviderResource.class);

	@GET
	@Produces(APPLICATION_OCTET_STREAM)
	public Response getFile(@QueryParam("uri") final String uri) {
		final URI fileURI = toFileURI(uri);

		if (fileURI == null) {
			return badRequest();
		}

		final Path filePath = toFilePath(fileURI);

		return (filePath != null ? file(filePath) : notFound());
	}

	private Path toFilePath(final URI fileURI) {
		Path filePath = null;

		try {
			filePath = get(fileURI);
		} catch (IllegalArgumentException | FileSystemNotFoundException
				| SecurityException e) {
			LOGGER.error(
					"There was an error trying to convert uri {} to path.",
					fileURI, e);
		}

		return filePath;
	}

	private URI toFileURI(String uri) {
		URI fileURI = null;

		try {
			fileURI = uri != null ? create(uri) : null;
		} catch (IllegalArgumentException e) {
			LOGGER.error(
					"There was an error trying to convert param {} to URI.",
					uri, e);
		}

		return fileURI;
	}

	private Response badRequest() {
		return status(BAD_REQUEST).build();
	}

	private Response file(final Path filePath) {
		final String fileName = filePath.getFileName().toString();

		LOGGER.debug("Found file {}.", fileName);

		return ok(filePath.toFile(), APPLICATION_OCTET_STREAM).header(
				"Content-Disposition",
				"attachment; filename=\"" + fileName + "\"").build();
	}

	private Response notFound() {
		return status(NOT_FOUND).build();
	}
}
