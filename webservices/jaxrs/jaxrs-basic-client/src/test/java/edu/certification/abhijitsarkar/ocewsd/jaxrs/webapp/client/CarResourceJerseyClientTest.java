package edu.certification.abhijitsarkar.ocewsd.jaxrs.webapp.client;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class CarResourceJerseyClientTest {
	private CarResourceJerseyClient client;
	private static final Logger logger = AppLogger
			.getInstance(CarResourceJerseyClientTest.class);

	public CarResourceJerseyClientTest() {
		try {
			client = new CarResourceJerseyClient();
		} catch (Exception e) {
			logger.error("Failed to instantiate client", e);
		}
	}

	@Before
	public void setUp() {
		Assert.assertNotNull("Failed to instantiate client", client);
	}

	@Test
	public void testSendBasicPathParamRequest() {
		Assert.assertEquals("Wrong make", "Mercedes",
				client.sendBasicPathParamRequest("/subresource/basicPathParam/Mercedes"));
	}

	@Test
	public void testSendMultiplePathParamRequest() {
		Assert.assertEquals(
				"Wrong response",
				"Make: Mercedes, Model: C250, Year: 2012",
				client.sendMultiplePathParamsRequest("/subresource/multiplePathParams/Mercedes-C250-2012"));
	}

	@Test
	public void testSendMultiplePathSegmentsRequest() {
		client.sendMultiplePathSegmentsRequest("/subresource/multiplePathSegments/Mercedes/C250;loaded=true/yes;moonroof/AMG/year/2012?make=Mercedes&model=C250");
	}

	@Test
	public void testSendMatrixParamRequest() {
		Assert.assertEquals("Wrong color", "black", client
				.sendMatrixParamRequest("/subresource/matrixParam/C250/2012;color=black"));
	}

	@Test
	public void testSendQueryParamRequest() {
		Assert.assertEquals("Wrong make", "Mercedes",
				client.sendQueryParamRequest("/subresource/queryParam", "make", "Mercedes"));
	}

	@Test
	public void testSendCookieParamRequest() {
		Assert.assertEquals("Wrong make", "Mercedes", client
				.sendCookieParamRequest("/subresource/cookieParam", "make", "Mercedes"));
	}

	@Test
	public void testSendHeaderParamRequest() {
		Assert.assertEquals("Wrong make", "Mercedes",
				client.sendHeaderParamRequest("/subresource/headerParam", "make", "Mercedes"));
	}

	@Test
	public void testSendFormParamRequest() {
		Assert.assertEquals("Wrong make", "Mercedes",
				client.sendFormParamRequest("/subresource/formParam", "make", "Mercedes"));
	}

	@Test
	public void testSendContextRequest1() {
		Assert.assertEquals("Wrong make", "Mercedes",
				client.sendContextRequest("/subresource/ctx/Mercedes/C250"));
	}
	
	@Test
	public void testSendContextRequest2() {
		Assert.assertEquals("Wrong model", "C250",
				client.sendContextRequest("/ctx/Mercedes/C250"));
	}
}
