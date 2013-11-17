package com.abien.business.coffee.boundary;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.abien.business.coffee.entity.Bean;

/**
 * 
 * @author adam-bien.com
 */
@ApplicationScoped
@Path("coffeebeans")
public class CoffeeBeansResource {

	@Context
	ResourceContext rc;
	Map<String, Bean> bc;

	@PostConstruct
	public void init() {
		this.bc = new ConcurrentHashMap<>();
	}

	@GET
	public Collection<Bean> allBeans() {
		return bc.values();
	}

	@GET
	@Path("{id}")
	public Bean bean(@PathParam("id") String id) {
		return bc.get(id);
	}

	@POST
	public Response add(Bean bean) {
		if (bean != null) {
			bc.put(bean.getName(), bean);
		}
		final URI id = URI.create(bean.getName());
		return Response.created(id).build();
	}

	@DELETE
	@Path("{id}")
	public void remove(@PathParam("id") String id) {
		bc.remove(id);
	}

	@Path("/roaster/{id}")
	public RoasterResource roaster() {
		return this.rc.initResource(new RoasterResource());
	}
}
