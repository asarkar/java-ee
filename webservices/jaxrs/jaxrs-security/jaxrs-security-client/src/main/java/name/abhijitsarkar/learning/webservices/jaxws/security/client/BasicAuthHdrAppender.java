package name.abhijitsarkar.learning.webservices.jaxws.security.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;

public class BasicAuthHdrAppender implements ClientRequestFilter {
	private final String token;

	public BasicAuthHdrAppender(String username, String password) {
		this.token = username + ":" + password;
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		MultivaluedMap<String, Object> headers = requestContext.getHeaders();
		final String base64EncCipher = getAuthToken();
		headers.putSingle("Authorization", base64EncCipher);
	}

	private String getAuthToken() throws UnsupportedEncodingException {
		return "Basic "
				+ DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
	}
}
