package name.abhijitsarkar.microservices.availability;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DateValidated
@Provider
@PreMatching
@Priority(Priorities.HEADER_DECORATOR)
public class DateValidatingFilter implements ContainerRequestFilter {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(DateValidatingFilter.class);

    private static final String DATE = "date";

    @Override
    public void filter(ContainerRequestContext requestContext)
	    throws IOException {

	UriInfo uriInfo = requestContext.getUriInfo();

	LocalDate today = LocalDate.now();

	if (isDatePresent(uriInfo)) {
	    return;
	}

	URI requestUri = uriInfo.getRequestUriBuilder()
		.replaceQueryParam(DATE, "{date}")
		.build(ISO_LOCAL_DATE.format(today));

	LOGGER.info("Original request URI: {}.", uriInfo.getRequestUri());
	LOGGER.info("Modified request URI: {}.", requestUri);

	requestContext.setRequestUri(requestUri);
    }

    private boolean isDatePresent(UriInfo uriInfo) {
	List<String> date = uriInfo.getQueryParameters().get(DATE);

	return date != null && !date.isEmpty();
    }
}
