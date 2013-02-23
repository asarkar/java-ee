package edu.certification.abhijitsarkar.ocewsd.jaxws.handler.client;

import org.junit.Assert;
import org.junit.Test;

public class ClientTest {
	Client client = new Client();

	@Test
	public void testSayHi() {
		Assert.assertEquals("Wrong greeting", "Hi Abhijit !",
				client.sayHi("Abhijit"));
	}
	
	@Test
	public void testSayHello() {
		Assert.assertEquals("Wrong greeting", "Hello Abhijit",
				client.sayHello("Abhijit"));
	}
}