package org.digester;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

/**
 * 
 * @author adam-bien.com
 */
@Provider
@PreMatching
public class HttpMethodOverrideEnabler implements ContainerRequestFilter {

	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		String override = requestContext.getHeaders().getFirst(
				"X-HTTP-Method-Override");
		if (override != null) {
			requestContext.setMethod(override);
		}
	}
}
