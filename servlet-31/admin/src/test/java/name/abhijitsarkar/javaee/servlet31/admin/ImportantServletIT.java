package name.abhijitsarkar.javaee.servlet31.admin;

import static java.lang.String.join;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getEncoder;
import static javax.servlet.http.HttpServletRequest.BASIC_AUTH;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.impl.client.HttpClients.createDefault;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

public class ImportantServletIT {
    private static final String URL = "http://localhost:8080/admin/";
    private static final String USERNAME_PASSWORD_DELIMITER = ":";
    private static final String AUTH_HEADER = "Authorization";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    @Test
    public void testGetContent() throws IOException {
	CloseableHttpClient httpclient = createDefault();
	HttpGet httpget = new HttpGet(URL);
	httpget.addHeader(AUTH_HEADER, newBase64EncCipher(USERNAME, PASSWORD));

	try (CloseableHttpResponse response = httpclient.execute(httpget)) {
	    assertEquals(SC_OK, response.getStatusLine().getStatusCode());

	    try (BufferedReader reader = new BufferedReader(
		    new InputStreamReader(response.getEntity().getContent()))) {
		String content = reader.lines().findAny().get();

		assertEquals("servlet3.1", content);
	    }
	}
    }

    @Test
    public void testGetContentUnauthorized() throws IOException {
	CloseableHttpClient httpclient = createDefault();
	HttpGet httpget = new HttpGet(URL);

	try (CloseableHttpResponse response = httpclient.execute(httpget)) {
	    assertEquals(SC_FORBIDDEN, response.getStatusLine().getStatusCode());
	}
    }

    private String newBase64EncCipher(String username, String password) {
	String token = join(USERNAME_PASSWORD_DELIMITER, username, password);

	return join(" ", BASIC_AUTH,
		new String(getEncoder().encode(token.getBytes(UTF_8))));
    }
}
