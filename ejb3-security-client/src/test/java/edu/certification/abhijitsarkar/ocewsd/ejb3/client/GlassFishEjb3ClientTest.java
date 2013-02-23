package edu.certification.abhijitsarkar.ocewsd.ejb3.client;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class GlassFishEjb3ClientTest {
	private GlassFishEjb3Client client;

	@Before
	public void setUp() {
		client = new GlassFishEjb3Client();
	}

	@Test
	public void testSecureMethod() {
		String message = client.secureMethod("user", "abhijitsarkar");

		Assert.assertNotNull("Message from Bean must not be null", message);
		Assert.assertEquals("Wrong message from Bean", "Hello user: user",
				message);
	}

	@Test
	public void testUnsecureMethod() {
		String message = client.unsecureMethod("guest", "abhijitsarkar");

		Assert.assertNotNull("Message from Bean must not be null", message);
		Assert.assertEquals("Wrong message from Bean", "Hello: guest", message);
	}
}
