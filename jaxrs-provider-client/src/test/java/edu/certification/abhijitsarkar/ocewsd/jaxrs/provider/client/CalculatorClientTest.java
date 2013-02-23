package edu.certification.abhijitsarkar.ocewsd.jaxrs.provider.client;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import edu.certification.abhijitsarkar.ocewsd.utility.logging.AppLogger;

public class CalculatorClientTest {
	private CalculatorClient client;
	private static final Logger logger = AppLogger
			.getInstance(CalculatorClientTest.class);

	public CalculatorClientTest() {
		try {
			client = new CalculatorClient();
		} catch (Exception e) {
			logger.error("Failed to instantiate client", e);
		}
	}

	@Test
	public void testAdd() {
		try {
			Assert.assertEquals("1 plus 1 must equal 2", 2, client.add(1, 1));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Shouldn't be here");
		}
	}
}
