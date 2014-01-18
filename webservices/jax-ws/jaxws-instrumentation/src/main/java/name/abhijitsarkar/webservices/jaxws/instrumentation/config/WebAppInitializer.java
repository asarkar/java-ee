package name.abhijitsarkar.webservices.jaxws.instrumentation.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.sun.xml.ws.transport.http.servlet.WSServlet;
import com.sun.xml.ws.transport.http.servlet.WSServletContextListener;

public class WebAppInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	private static final String activeSpringProfiles = System
			.getProperty("spring.profiles.active");

	@Override
	protected Class<?>[] getRootConfigClasses() {
		Class<?>[] rootConfigClasses = new Class<?>[] { AppConfig.class };

		return rootConfigClasses;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		Class<?>[] servletConfigClasses = new Class<?>[] { DispatcherConfig.class };

		return servletConfigClasses;
	}

	@Override
	protected String[] getServletMappings() {
		String[] servletMappings = new String[] { "/client/*" };

		return servletMappings;
	}

	@Override
	protected void registerContextLoaderListener(ServletContext servletContext) {
		super.registerContextLoaderListener(servletContext);

		if (WebAppInitializer.isProfileActive("metro")) {
			registerJaxWsContextListener(servletContext);
		}
	}

	private static boolean isProfileActive(String profileName) {
		final boolean isProfileActive = activeSpringProfiles != null
				&& activeSpringProfiles.contains(profileName);

		System.out.println("Spring profile \"" + profileName + "\" is"
				+ (isProfileActive ? " " : " NOT ") + "active");

		return isProfileActive;
	}

	private void registerJaxWsContextListener(ServletContext servletContext) {
		// Register JAX-WS context listener
		servletContext.addListener(new WSServletContextListener());
	}

	@Override
	protected String getServletName() {
		return "dispatcher";
	}

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		super.onStartup(servletContext);

		if (WebAppInitializer.isProfileActive("metro")) {
			registerJaxWsServlet(servletContext);
		}
	}

	private void registerJaxWsServlet(ServletContext servletContext) {
		// Register and map the JAX-WS servlet
		ServletRegistration.Dynamic jaxWs = servletContext.addServlet("jax-ws",
				new WSServlet());
		jaxWs.addMapping("/calculator/*");
	}
}
