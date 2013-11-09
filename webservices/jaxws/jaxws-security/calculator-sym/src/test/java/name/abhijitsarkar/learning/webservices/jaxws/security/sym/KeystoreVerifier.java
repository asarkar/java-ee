package name.abhijitsarkar.learning.webservices.jaxws.security.sym;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;

import org.junit.Assert;
import org.junit.Test;

public class KeystoreVerifier {

	@Test
	public void testCanLoadJCEKSKeystore() throws Exception {

		KeyStore ks = KeyStore.getInstance("JCEKS");

		char[] password = "password".toCharArray();

		URL keystore = getClass().getResource("/META-INF/calc-enc-store.jceks");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(keystore.getPath());
			ks.load(fis, password);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}

		Assert.assertTrue("Keystore should contain calcenc alias",
				ks.containsAlias("calcenc"));

		System.out.println("Keystore Provider: " + ks.getProvider().getInfo());
	}

	@Test(expected = IOException.class)
	public void testCannotLoadJCEKSKeystoreUsingDefaultType() throws Exception {

		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

		char[] password = "password".toCharArray();

		URL keystore = getClass().getResource("/META-INF/calc-enc-store.jceks");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(keystore.getPath());
			ks.load(fis, password);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}

		Assert.assertTrue("Keystore should contain calcenc alias",
				ks.containsAlias("calcenc"));

		System.out.println("Keystore Provider: " + ks.getProvider().getInfo());
	}
}
