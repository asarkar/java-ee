package edu.certification.abhijitsarkar.ocewsd.jaxws.ejb.endpoint.client;

import junit.framework.Assert;

import org.junit.Test;

import edu.certification.abhijitsarkar.ocewsd.jaxws.ejb.endpoint.Time;
import edu.certification.abhijitsarkar.ocewsd.jaxws.ejb.endpoint.client.Client;

public class ClientTest {

	public ClientTest() {
		client = new Client();
	}

	@Test
	public void testGetCurrentTime() {
		Time time = client.getCurrentTime();
		Assert.assertEquals("Wrong time zone", TIME_ZONE, time.getTimeZone());
	}

	@Test
	public void testGetCurrentTimeAfterHttpBasicAuthentication() {
		Time time = client.getCurrentTimeAfterHttpBasicAuthentication();
		Assert.assertEquals("Wrong time zone", TIME_ZONE, time.getTimeZone());
	}

	@Test
	public void testGetCurrentTimeAfterDeclarativeRoleBasedAuthorization() {
		Time time = client
				.getCurrentTimeAfterDeclarativeRoleBasedAuthorization();
		Assert.assertEquals("Wrong time zone", TIME_ZONE, time.getTimeZone());
	}

	@Test
	public void testGetCurrentTimeAfterProgrammaticRoleBasedAuthorization() {
		Time time = client
				.getCurrentTimeAfterProgrammaticRoleBasedAuthorization();
		Assert.assertEquals("Wrong time zone", TIME_ZONE, time.getTimeZone());
	}

	@Test
	public void testGetCurrentTimeAfterUserPrincipalAuthentication() {
		Time time = client.getCurrentTimeAfterUserPrincipalAuthentication();
		Assert.assertEquals("Wrong time zone", TIME_ZONE, time.getTimeZone());
	}

	@Test
	public void testGetCurrentTimeAfterProgrammaticAuthentication() {
		Time time = client.getCurrentTimeAfterProgrammaticAuthentication();
		Assert.assertEquals("Wrong time zone", TIME_ZONE, time.getTimeZone());
	}

	private final Client client;
	private final static String TIME_ZONE = "America/New_York";
}
