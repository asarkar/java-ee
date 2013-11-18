package org.digester;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

/**
 * 
 * @author adam-bien.com
 */
// @Provider
public class LoggerRegistration implements DynamicFeature {

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		String debug = System.getProperty("jax-rs.traffic");
		if (debug != null) {
			context.register(new TrafficLogger());
		}
		String verification = System.getProperty("jax-rs.verification");
		if (verification != null) {
			context.register(new PayloadVerifier());
		}
	}
}
