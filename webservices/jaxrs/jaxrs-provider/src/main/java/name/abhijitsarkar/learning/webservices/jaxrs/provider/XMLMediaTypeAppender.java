package name.abhijitsarkar.learning.webservices.jaxrs.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

// A Java EE container can scan and register classes annotated with @Provider; Servlet containers like Jetty can't so
// Providers need to be registered with the Application class.

@Provider
@PreMatching
// Add XML to the list of acceptable content types as that's the only custom
// EntityProvider we support
public class XMLMediaTypeAppender implements ContainerRequestFilter,
		ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		List<MediaType> mediaTypes = requestContext.getAcceptableMediaTypes();

		if (mediaTypes.contains(MediaType.APPLICATION_XML_TYPE)) {
			return;
		}

		MultivaluedMap<String, String> requestHdrs = requestContext
				.getHeaders();
		List<String> originalAcceptHdr = requestHdrs.get("Accept");

		requestHdrs.put("Original-Accept", originalAcceptHdr);

		List<String> acceptHdr = new ArrayList<String>(originalAcceptHdr);
		acceptHdr.add(MediaType.APPLICATION_XML);

		requestHdrs.put("Accept", acceptHdr);
	}

	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		MultivaluedMap<String, String> requestHdrs = requestContext
				.getHeaders();
		List<String> originalAcceptHdr = requestHdrs.get("Original-Accept");

		if (originalAcceptHdr != null) {
			responseContext.getHeaders().putSingle("Original-Accept",
					originalAcceptHdr);

			requestHdrs.remove("Original-Accept");
		}
	}
}
