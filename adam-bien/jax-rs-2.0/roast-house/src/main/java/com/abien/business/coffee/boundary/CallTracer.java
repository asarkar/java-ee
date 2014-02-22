package com.abien.business.coffee.boundary;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * 
 * @author adam-bien.com
 */
public class CallTracer {

	@AroundInvoke
	public Object trace(InvocationContext ic) throws Exception {
		System.out.println("-- " + ic.getMethod());
		return ic.proceed();
	}
}
