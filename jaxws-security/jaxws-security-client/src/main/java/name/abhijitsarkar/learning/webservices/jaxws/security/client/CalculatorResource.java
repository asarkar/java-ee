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

import name.abhijitsarkar.learning.webservices.jaxws.security.ut.client.generated.CalculatorUTService;
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

	@PostConstruct
	public void validate() {
		if (calculatorUT == null) {
			logger.error("Calculcator DI failed");
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Path("ut")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public int add(@QueryParam(value = "arg1") int i,
			@QueryParam(value = "arg2") int j) {

		int sum = calculatorUT.getCalculatorUT().add(i, j);

		logger.info("Sum: " + sum);

		return sum;
	}
}
