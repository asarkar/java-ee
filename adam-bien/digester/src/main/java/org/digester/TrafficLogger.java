package org.digester;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 * 
 * @author adam-bien.com
 */
@Tracked
@Provider
public class TrafficLogger implements ContainerRequestFilter,
		ContainerResponseFilter {

	private static final Logger LOG = Logger.getLogger(TrafficLogger.class
			.getName());

	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		log(requestContext);
	}

	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		log(responseContext);
	}

	void log(ContainerRequestContext requestContext) {
		SecurityContext securityContext = requestContext.getSecurityContext();
		String authentication = securityContext.getAuthenticationScheme();
		Principal userPrincipal = securityContext.getUserPrincipal();
		String securityInfo = " ";
		if (authentication != null || userPrincipal != null) {
			securityInfo = " Authentication: " + authentication
					+ " Principal: " + userPrincipal + " ";
		}

		UriInfo uriInfo = requestContext.getUriInfo();
		String method = requestContext.getMethod();
		List<Object> matchedResources = uriInfo.getMatchedResources();
		String resources = "";
		for (Object resource : matchedResources) {
			resources += resource.getClass().getName();
		}
		LOG.log(Level.INFO, "{0}[{1}] {2} -> {3}", new Object[] { securityInfo,
				method, uriInfo.getPath(), resources });
	}

	void log(ContainerResponseContext responseContext) {
		MultivaluedMap<String, String> stringHeaders = responseContext
				.getStringHeaders();
		Set<String> keys = stringHeaders.keySet();
		StringBuilder headers = new StringBuilder();
		for (String key : keys) {
			headers.append(key).append(":").append(stringHeaders.get(key));
		}
		String headerInfo = "";

		if (!keys.isEmpty()) {
			headerInfo = "Response: " + headers.toString();
		}
		Object entity = responseContext.getEntity();
		String entityInfo = " ";
		if (entity != null) {
			entityInfo = entity.getClass().getName();
		}
		LOG.log(Level.INFO, "{0} <- {1}",
				new Object[] { headerInfo, entityInfo });
	}
}
