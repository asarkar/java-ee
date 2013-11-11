package name.abhijitsarkar.learning.webservices.jaxws.security.ejb;

import java.security.Principal;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

@WebService(portName = "CalculatorEJB", name = "CalculatorEJB", serviceName = "CalculatorEJBService", targetNamespace = "http://abhijitsarkar.name/learning/webservices/jaxws/security/CalculatorEJB/")
@Stateless(name = "CalculatorEJB")
@Local(value = CalculatorEJBSEI.class)
@DeclareRoles({ "bigshot" })
public class CalculatorEJB implements CalculatorEJBSEI {

	// Needs a beans.xml for CDI to work
	@Resource
	private WebServiceContext wsCtx;

	// This can be used too
	@Resource
	private SessionContext sessionCtx;

	@WebMethod(operationName = "add")
	@RolesAllowed(value = "bigshot")
	public int add(@WebParam(name = "i") final int i,
			@WebParam(name = "j") final int j) {

		Principal cp = wsCtx.getUserPrincipal();
		String principalName = (cp != null ? cp.getName() : null);
		System.out.println("Principal name from wxCtx: " + principalName);
		System.out.println("isUserInRole(bigshot): "
				+ wsCtx.isUserInRole("bigshot"));

		cp = sessionCtx.getCallerPrincipal();
		principalName = (cp != null ? cp.getName() : null);
		System.out.println("Principal name from sessionCtx: " + principalName);
		System.out.println("isCallerInRole(bigshot): "
				+ sessionCtx.isCallerInRole("bigshot"));

		return i + j;
	}
}
