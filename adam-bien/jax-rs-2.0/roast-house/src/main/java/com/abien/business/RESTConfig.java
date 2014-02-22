package com.abien.business;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.digester.Tracked;

/**
 * 
 * @author adam-bien.com
 */
@Tracked
@ApplicationPath("api")
public class RESTConfig extends Application {
}
