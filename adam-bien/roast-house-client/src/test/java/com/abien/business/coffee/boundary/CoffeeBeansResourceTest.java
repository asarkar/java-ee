package com.abien.business.coffee.boundary;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.util.Collection;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientFactory;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.digester.PayloadVerifier;
import org.junit.Before;
import org.junit.Test;

public class CoffeeBeansResourceTest {

	Client client;
	WebTarget root;

	@Before
	public void initClient() {
		this.client = ClientFactory.newClient().register(PayloadVerifier.class);
		this.root = this.client
				.target("http://localhost:8080/roast-house/api/coffeebeans");
	}

	@Test
	public void crud() {
		Bean origin = new Bean("arabica", RoastType.DARK, "mexico");
		final String mediaType = MediaType.APPLICATION_XML;
		final Entity<Bean> entity = Entity.entity(origin, mediaType);
		Response response = this.root.request().post(entity, Response.class);
		assertThat(response.getStatus(), is(201));

		Bean result = this.root.path(origin.getName()).request(mediaType)
				.get(Bean.class);
		assertThat(result, is(origin));
		Collection<Bean> allBeans = this.root.request().get(
				new GenericType<Collection<Bean>>() {
				});
		assertThat(allBeans.size(), is(1));
		assertThat(allBeans, hasItem(origin));

		response = this.root.path(origin.getName()).request(mediaType)
				.delete(Response.class);
		assertThat(response.getStatus(), is(204));

		response = this.root.path(origin.getName()).request(mediaType)
				.get(Response.class);
		assertThat(response.getStatus(), is(204));
	}

	@Test
	public void roasterAsync() throws InterruptedException {
		Bean origin = new Bean("arabica", RoastType.DARK, "mexico");
		final String mediaType = MediaType.APPLICATION_XML;
		final Entity<Bean> entity = Entity.entity(origin, mediaType);
		this.root.path("roaster").path("roast-id").request().async()
				.post(entity, new InvocationCallback<Bean>() {
					public void completed(Bean rspns) {
					}

					public void failed(Throwable thrwbl) {
					}
				});
	}

	@Test
	public void roasterFuture() throws Exception {
		Bean origin = new Bean("arabica", RoastType.DARK, "mexico");
		final String mediaType = MediaType.APPLICATION_XML;
		final Entity<Bean> entity = Entity.entity(origin, mediaType);
		Future<Response> future = this.root.path("roaster").path("roast-id")
				.request().async().post(entity);
		Response response = future.get(5000, TimeUnit.SECONDS);
		Object result = response.getEntity();
		assertNotNull(result);
		// assertThat(roasted.getBlend(),containsString("The dark side of the bean"));
	}

	@Test
	public void template() throws Exception {
		String rootPath = this.root.getUri().getPath();
		URI uri = this.root.path("{0}/{last}").resolveTemplate("0", "hello")
				.resolveTemplate("last", "REST").getUri();
		assertThat(uri.getPath(), is(rootPath + "/hello/REST"));
	}
}
