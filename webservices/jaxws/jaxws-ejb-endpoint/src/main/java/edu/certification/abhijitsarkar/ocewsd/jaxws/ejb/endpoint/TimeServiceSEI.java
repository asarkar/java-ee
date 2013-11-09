package edu.certification.abhijitsarkar.ocewsd.jaxws.ejb.endpoint;

import javax.jws.WebService;

import edu.certification.abhijitsarkar.ocewsd.jaxws.utility.Time;

@WebService
public interface TimeServiceSEI {

	public Time getCurrentTime();

	public Time getCurrentTimeAfterHttpBasicAuthentication();

	public Time getCurrentTimeAfterDeclarativeRoleBasedAuthorization();

	public Time getCurrentTimeAfterProgrammaticRoleBasedAuthorization();

	public Time getCurrentTimeAfterUserPrincipalAuthentication();

	public Time getCurrentTimeAfterProgrammaticAuthentication();
}
