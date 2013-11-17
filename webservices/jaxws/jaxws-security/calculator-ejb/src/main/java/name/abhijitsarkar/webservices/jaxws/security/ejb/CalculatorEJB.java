package name.abhijitsarkar.webservices.jaxws.security.ejb;

import java.security.Principal;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

@WebService(portName = "CalculatorEJB", name = "CalculatorEJB", serviceName = "CalculatorEJBService", targetNamespace = "http://abhijitsarkar.name/webservices/jaxws/security/CalculatorEJB/")
@Stateless(name = "CalculatorEJB")
@Local(CalculatorEJBLocal.class)
@Remote(CalculatorEJBRemote.class)
@DeclareRoles({ "celebrity" })
public class CalculatorEJB implements CalculatorEJBRemote {

	@Resource
	private WebServiceContext wsCtx;

	// This can be used too
	@Resource
	private SessionContext sessionCtx;

	@WebMethod(operationName = "add")
	@RolesAllowed(value = "celebrity")
	public int add(@WebParam(name = "i") final int i,
			@WebParam(name = "j") final int j) {

		Principal cp = wsCtx.getUserPrincipal();
		String principalName = (cp != null ? cp.getName() : null);
		System.out.println("Principal name from wxCtx: " + principalName);
		System.out.println("isUserInRole(celebrity): "
				+ wsCtx.isUserInRole("celebrity"));

		cp = sessionCtx.getCallerPrincipal();
		principalName = (cp != null ? cp.getName() : null);
		System.out.println("Principal name from sessionCtx: " + principalName);
		System.out.println("isCallerInRole(celebrity): "
				+ sessionCtx.isCallerInRole("celebrity"));

		return i + j;
	}
}
