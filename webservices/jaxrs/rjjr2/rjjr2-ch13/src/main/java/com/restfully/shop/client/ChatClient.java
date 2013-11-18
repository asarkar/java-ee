package com.restfully.shop.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class ChatClient {
	public static void main(String[] args) throws Exception {
		String name = "test";

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();

		final Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/ch13/services/chat");

		target.request().async().get(new InvocationCallback<Response>() {
			@Override
			public void completed(Response response) {
				Link next = response.getLink("next");
				String message = response.readEntity(String.class);
				System.out.println();
				System.out.print(message);
				System.out.println();
				System.out.print("> ");
				client.target(next).request().async().get(this);
			}

			@Override
			public void failed(Throwable throwable) {
				System.err.println("FAILURE!");
			}
		});

		while (true) {
			System.out.print("> ");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			String message = br.readLine();
			target.request().post(Entity.text(name + ": " + message));
		}

	}
}
