package com.github.mkalin.jwsur2.ch5.transportlevel.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import com.github.mkalin.jwsur2.ch5.transportlevel.client.generated.Echo;
import com.github.mkalin.jwsur2.ch5.transportlevel.client.generated.EchoService;

public class EchoClient {
	private static final String defaultUrl = "http://localhost:8080/ch5/echo";

	public static void main(String[] args) {
		Echo port = new EchoService().getEchoPort();
		Map<String, Object> requestContext = ((BindingProvider) port)
				.getRequestContext();

		String url = (args.length >= 2) ? (args[0] + args[1]) : defaultUrl;
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
		// Add application-specific HTTP header blocks.
		Map<String, Object> myHeaders = new HashMap<String, Object>();
		myHeaders.put("Accept-Encoding", Collections.singletonList("gzip"));
		myHeaders.put("Greeting", Collections.singletonList("Hello, world!"));
		requestContext.put(MessageContext.HTTP_REQUEST_HEADERS, myHeaders);
		port.echo("Have a nice day :)");
		Map<String, Object> responseContext = ((BindingProvider) port)
				.getResponseContext();
		dump(responseContext, "");
	}

	@SuppressWarnings("rawtypes")
	private static void dump(Map map, String indent) {
		Set keys = map.keySet();

		Object value = null;
		for (Object key : keys) {
			value = map.get(key);
			System.out.println(indent + key + " : " + value);

			if (value instanceof Map) {
				dump((Map) value, indent += " ");
			}
		}
	}
}
