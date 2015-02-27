package com.abhijitsarkar.microservices.discovery;

import java.net.URL;
import java.util.concurrent.ConcurrentMap;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("resgistry")
public class ServiceRegistry {
    private ConcurrentMap<String, URL> registry;
    
    @POST
    public Response createEntry(@FormParam("name") String name, @FormParam("url") URL url) {
	registry.put(name, url);
	
	return Response.created(null).build();
    }
    
    public Response getServiceURL(String name) {
	registry.
    }
}
