package name.abhijitsarkar.webservices.jaxrs.security.pgm.auth;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

public class MustBeCelebrityFeature implements DynamicFeature {

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		Method method = resourceInfo.getResourceMethod();

		boolean mustBeCelebrity = (method != null && method
				.getAnnotation(MustBeCelebrity.class) != null);

		if (mustBeCelebrity) {
			context.register(new MustBeCelebrityFilter());
		}
	}

	@Priority(Priorities.AUTHORIZATION)
	// authorization filter - should go after any authentication filters
	private static class MustBeCelebrityFilter implements
			ContainerRequestFilter {

		@Override
		public void filter(ContainerRequestContext requestContext)
				throws IOException {
			SecurityContext secContext = requestContext.getSecurityContext();

			if (secContext.isUserInRole("celebrity")) {
				return;
			}

			throw new NotAuthorizedException(secContext.getUserPrincipal()
					+ " is not authorized for this operation.",
					Response.Status.UNAUTHORIZED);
		}
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface MustBeCelebrity {
	}
}
