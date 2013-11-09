package edu.certification.abhijitsarkar.ocewsd.jaxws.servlet.container;

import java.util.Date;

import javax.jws.WebService;

@WebService
public interface TimeServiceSEI {

	public Date getCurrentTime(final String timezone) throws UnsupportedTimeZoneException;
}
