package edu.certification.abhijitsarkar.ocewsd.jaxrs.ejb.client;

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
	public void testSendRequest() {
		Assert.assertEquals("Wrong make", "Mercedes",
				client.sendRequest("Mercedes"));
	}
}
