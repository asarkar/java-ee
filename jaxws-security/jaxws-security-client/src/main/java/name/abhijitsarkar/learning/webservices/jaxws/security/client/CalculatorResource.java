package name.abhijitsarkar.learning.webservices.jaxws.security.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import name.abhijitsarkar.learning.webservices.jaxws.security.client.sym.generated.CalculatorSymService;
import name.abhijitsarkar.learning.webservices.jaxws.security.client.ut.generated.CalculatorUTService;
import name.abhijitsarkar.util.logging.AppLogger;

import org.apache.log4j.Logger;

@Path("/")
public class CalculatorResource {
	private static final Logger logger = AppLogger
			.getInstance(CalculatorResource.class);

	private CalculatorUTService calculatorUT;

	private CalculatorSymService calculatorSym;

	public CalculatorResource() {
		calculatorUT = new CalculatorUTService();
		calculatorSym = new CalculatorSymService();
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
}
