package name.abhijitsarkar.learning.webservices.jaxws.security.client;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;

import name.abhijitsarkar.learning.webservices.jaxws.security.client.ejb.generated.CalculatorEJB;
import name.abhijitsarkar.learning.webservices.jaxws.security.client.ejb.generated.CalculatorEJBService;
import name.abhijitsarkar.learning.webservices.jaxws.security.client.sym.generated.CalculatorSymService;
import name.abhijitsarkar.learning.webservices.jaxws.security.client.ut.generated.CalculatorUTService;
import name.abhijitsarkar.util.logging.AppLogger;

import org.apache.log4j.Logger;

@Path("/")
@Stateless
public class CalculatorResource {
	private static final Logger logger = AppLogger
			.getInstance(CalculatorResource.class);

	@WebServiceRef
	private CalculatorUTService calculatorUTService;

	@WebServiceRef
	private CalculatorSymService calculatorSymService;

	@WebServiceRef
	private CalculatorEJBService calculatorEJBService;

	@Path("ut")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int addUsingCalculatorUT(@QueryParam(value = "arg1") int i,
			@QueryParam(value = "arg2") int j) {

		int sum = calculatorUTService.getCalculatorUT().add(i, j);

		logger.info("Sum: " + sum);

		return sum;
	}

	@Path("sym")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int addUsingCalculatorSym(@QueryParam(value = "arg1") int i,
			@QueryParam(value = "arg2") int j) {

		int sum = calculatorSymService.getCalculatorSym().add(i, j);

		logger.info("Sum: " + sum);

		return sum;
	}

	@Path("ejb")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int addUsingCalculatorEJB(@QueryParam(value = "arg1") int i,
			@QueryParam(value = "arg2") int j) {
		
		CalculatorEJB calcEJB = calculatorEJBService.getCalculatorEJB();

		BindingProvider prov = (BindingProvider) calcEJB;

		prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "bob");
		prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
				"password");

		int sum = calcEJB.add(i, j);

		logger.info("Sum: " + sum);

		return sum;
	}
}
