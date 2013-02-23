package edu.certification.abhijitsarkar.ocewsd.jaxws.addressing.client;

import org.junit.Assert;
import org.junit.Test;

public class ClientTest {
	private Client client = new Client();

	@Test
	public void testAdd() {
		Assert.assertEquals("1 plus 1 must equal 2", 2, client.add(1, 1));
	}
}
