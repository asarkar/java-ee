package com.restfully.shop.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("services")
public class ShoppingApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public ShoppingApplication() {
		singletons.add(new CustomerResource());
		singletons.add(new CustomerChat());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
