package edu.certification.abhijitsarkar.ocewsd.ejb3;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

@Stateless
@LocalBean
@DeclareRoles({ "user", "guest" })
public class SecureBean implements SecureBeanRemote {
	@Resource
	SessionContext context;

	@RolesAllowed("user")
	public String secureMethod() {
		return "Hello user: " + context.getCallerPrincipal().getName();
	}

	@PermitAll
	public String unsecureMethod() {
		return "Hello: " + context.getCallerPrincipal().getName();
	}
}
