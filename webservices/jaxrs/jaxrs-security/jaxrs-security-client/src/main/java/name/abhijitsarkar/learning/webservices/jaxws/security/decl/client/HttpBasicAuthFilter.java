package name.abhijitsarkar.learning.webservices.jaxws.security.decl.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;

public class HttpBasicAuthFilter implements ClientRequestFilter {
	private final String token;

	public HttpBasicAuthFilter(String username, String password) {
		this.token = username + ":" + password;
	}

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		MultivaluedMap<String, Object> headers = requestContext.getHeaders();
		final String base64EncCipher = getAuthToken();
		headers.putSingle("Authorization", base64EncCipher);
	}

	public String getAuthToken() throws UnsupportedEncodingException {
		return "Basic "
				+ DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
	}
}
