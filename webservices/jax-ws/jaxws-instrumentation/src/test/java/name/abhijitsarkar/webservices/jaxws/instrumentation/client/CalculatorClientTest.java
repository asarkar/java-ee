package name.abhijitsarkar.webservices.jaxws.instrumentation.client;

import name.abhijitsarkar.webservices.jaxws.instrumentation.config.AppConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class CalculatorClientTest {
	@Autowired
	CalculatorClient client;

	// Enable Metro JMX monitoring
	// @BeforeClass
	// public static void setUpOnce() {
	// System.setProperty("com.sun.xml.ws.monitoring.endpoint", "true");
	// System.setProperty("com.sun.xml.ws.monitoring.client", "true");
	// System.setProperty("com.sun.management.jmxremote.port", "9010");
	// System.setProperty("com.sun.management.jmxremote.local.only", "false");
	// System.setProperty("com.sun.management.jmxremote.authenticate", "false");
	// System.setProperty("com.sun.management.jmxremote.ssl", "false");
	// }
	//
	@SuppressWarnings("static-access")
	@Test
	public void testInvokeAdd() {
		for (int i = 0, j = 100; i < 100; i++, j--) {
			System.out.println("Sum of " + i + " and " + j + " is "
					+ client.invokeAdd(new AddRequest(i, j)));

			try {
				// Sleep for 10 sec so that we can monitor via JConsole
				Thread.currentThread().sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
