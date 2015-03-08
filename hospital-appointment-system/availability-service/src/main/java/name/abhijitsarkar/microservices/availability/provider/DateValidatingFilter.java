package name.abhijitsarkar.microservices.availability.provider;

import static java.time.DayOfWeek.MONDAY;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

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

/* c.f. http://docs.oracle.com/javaee/7/api/javax/ws/rs/container/PreMatching.html
 * "Any named binding annotations will be ignored on a component annotated with the @PreMatching annotation." */
//@DateValidated
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

	if (isDatePresent(uriInfo)) {
	    return;
	}

	LocalDate nextMonday = LocalDate.now().with(nextOrSame(MONDAY));

	URI requestUri = uriInfo.getRequestUriBuilder()
		.replaceQueryParam(DATE, "{date}")
		.build(ISO_LOCAL_DATE.format(nextMonday));

	LOGGER.info("Original request URI: {}.", uriInfo.getRequestUri());
	LOGGER.info("Modified request URI: {}.", requestUri);

	requestContext.setRequestUri(requestUri);
    }

    private boolean isDatePresent(UriInfo uriInfo) {
	List<String> date = uriInfo.getQueryParameters().get(DATE);

	return date != null && !date.isEmpty();
    }
}
