package edu.certification.abhijitsarkar.ocewsd.jaxws.provider.client;

import junit.framework.Assert;

import org.junit.Test;

public class JAXWSProviderDispatchClientTest {

	@Test
	public void testInvoke1() {
		Assert.assertEquals("Wrong sum", 3, client.invoke1(1, 2));
		Assert.assertEquals("Wrong sum", 0, client.invoke1(0, 0));
		Assert.assertEquals("Wrong sum", 1, client.invoke1(-1, 2));
	}

	@Test
	public void testInvoke2() {
		Assert.assertEquals("Wrong sum", 3, client.invoke2(1, 2));
		Assert.assertEquals("Wrong sum", 0, client.invoke2(0, 0));
		Assert.assertEquals("Wrong sum", 1, client.invoke2(-1, 2));
	}

	private final JAXWSProviderDispatchClient client = new JAXWSProviderDispatchClient();
}
