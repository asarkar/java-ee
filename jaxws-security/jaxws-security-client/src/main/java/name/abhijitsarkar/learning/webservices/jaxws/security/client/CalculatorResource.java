package name.abhijitsarkar.learning.webservices.jaxws.security.client;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import name.abhijitsarkar.learning.webservices.jaxws.security.client.ssl.generated.CalculatorSSLService;
import name.abhijitsarkar.learning.webservices.jaxws.security.client.sym.generated.CalculatorSymService;
import name.abhijitsarkar.learning.webservices.jaxws.security.client.ut.generated.CalculatorUTService;
import name.abhijitsarkar.util.logging.AppLogger;

import org.apache.log4j.Logger;

@Path("/")
@ManagedBean
public class CalculatorResource {
	private static final Logger logger = AppLogger
			.getInstance(CalculatorResource.class);

	// CDI @Inject is the only injection that works.
	// It is said that @WebServiceRef annotation should work but it didn't.

	// See this - http://docs.oracle.com/javaee/6/tutorial/doc/gipjf.html

	// @WebServiceRef(wsdlLocation =
	// "http://localhost:8080/calculator-ut/CalculatorUTService")
	@Inject
	private CalculatorUTService calculatorUT;

	@Inject
	private CalculatorSymService calculatorSym;
	
	@Inject
	private CalculatorSSLService calculatorSSL;

	@PostConstruct
	public void validate() {
		if (calculatorUT == null) {
			logger.error("Calculcator UT DI failed");
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		if (calculatorSym == null) {
			logger.error("Calculcator Sym DI failed");
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		
		if (calculatorSSL == null) {
			logger.error("Calculcator SSL DI failed");
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("ut")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int addUsingCalculatorUT(@QueryParam(value = "arg1") int i,
			@QueryParam(value = "arg2") int j) {

		int sum = calculatorUT.getCalculatorUT().add(i, j);

		logger.info("Sum: " + sum);

		return sum;
	}

	@Path("sym")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int addUsingCalculatorSym(@QueryParam(value = "arg1") int i,
			@QueryParam(value = "arg2") int j) {

		int sum = calculatorSym.getCalculatorSym().add(i, j);

		logger.info("Sum: " + sum);

		return sum;
	}
	
	@Path("ssl")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int addUsingCalculatorSSL(@QueryParam(value = "arg1") int i,
			@QueryParam(value = "arg2") int j) {

		int sum = calculatorSSL.getCalculatorSSL().add(i, j);

		logger.info("Sum: " + sum);

		return sum;
	}
}
