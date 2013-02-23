package edu.certification.abhijitsarkar.ocewsd.jaxws.pojo.endpoint.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;

import edu.certification.abhijitsarkar.ocewsd.jaxws.pojo.endpoint.Time;
import edu.certification.abhijitsarkar.ocewsd.jaxws.pojo.endpoint.TimeService;
import edu.certification.abhijitsarkar.ocewsd.jaxws.pojo.endpoint.TimeServiceSEI;

public class Client {

	public Time getCurrentTime() {
		TimeServiceSEI timeService = getPort();
		BindingProvider bp = (BindingProvider) timeService;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "user");
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
				"abhijitsarkar");
		setSoapAction("no-auth", bp);
		return timeService.getCurrentTime();
	}

	public Time getCurrentTimeAfterHttpBasicAuthentication() {
		TimeServiceSEI timeService = getPort();
		BindingProvider bp = (BindingProvider) timeService;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "user");
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
				"abhijitsarkar");
		setSoapAction("http-basic-auth", bp);

		// Creating HTTP headers
		@SuppressWarnings("unchecked")
		Map<String, List<String>> requestHdrs = (Map<String, List<String>>) bp
				.getRequestContext().get(MessageContext.HTTP_REQUEST_HEADERS);
		if (requestHdrs == null) {
			requestHdrs = new HashMap<String, List<String>>();
		}
		requestHdrs.put(BindingProvider.USERNAME_PROPERTY,
				Arrays.asList("asarkar"));
		requestHdrs.put(BindingProvider.PASSWORD_PROPERTY,
				Arrays.asList("abhijitsarkar"));

		bp.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS,
				requestHdrs);

		return timeService.getCurrentTimeAfterHttpBasicAuthentication();
	}

	public Time getCurrentTimeAfterDeclarativeRoleBasedAuthorization() {
		TimeServiceSEI timeService = getPort();
		BindingProvider bp = (BindingProvider) timeService;
		setSoapAction("decl-auth", bp);
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "user");
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
				"abhijitsarkar");
		return timeService
				.getCurrentTimeAfterDeclarativeRoleBasedAuthorization();
	}

	public Time getCurrentTimeAfterProgrammaticRoleBasedAuthorization() {
		TimeServiceSEI timeService = getPort();
		BindingProvider bp = (BindingProvider) timeService;
		setSoapAction("no-auth", bp);
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "user");
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
				"abhijitsarkar");
		return timeService
				.getCurrentTimeAfterProgrammaticRoleBasedAuthorization();
	}

	public Time getCurrentTimeAfterUserPrincipalAuthentication() {
		TimeServiceSEI timeService = getPort();
		BindingProvider bp = (BindingProvider) timeService;
		setSoapAction("no-auth", bp);
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "user");
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
				"abhijitsarkar");
		return timeService
				.getCurrentTimeAfterProgrammaticRoleBasedAuthorization();
	}

	public Time getCurrentTimeAfterProgrammaticAuthentication() {
		TimeServiceSEI timeService = getPort();
		// only works in JAX-WS RI
		WSBindingProvider bp = (WSBindingProvider) timeService;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "user");
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
				"abhijitsarkar");
		setSoapAction("pgm-auth", bp);
		bp.setOutboundHeaders(Headers.create(new QName("secret-access-key"),
				"asarkar"));
		return timeService.getCurrentTimeAfterProgrammaticAuthentication();
	}

	private TimeServiceSEI getPort() {
		return new TimeService().getTimeServicePort();
	}

	private void setSoapAction(String soapAction, BindingProvider bp) {
		bp.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY,
				true);
		bp.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY,
				soapAction);
	}
}
