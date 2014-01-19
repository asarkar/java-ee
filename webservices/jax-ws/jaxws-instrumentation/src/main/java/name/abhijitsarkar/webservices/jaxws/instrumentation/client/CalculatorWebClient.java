package name.abhijitsarkar.webservices.jaxws.instrumentation.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CalculatorWebClient {
	@Autowired
	CalculatorClient calClient;

	@RequestMapping(value = "/client", method = RequestMethod.GET)
	@ResponseBody
	// Spring sets the model attributes to zero even if the request has no such
	// parameters
	public String add(@ModelAttribute AddRequest addRequest) {

		return "<h1>The sum of " + addRequest.getArg0() + " and "
				+ addRequest.getArg1() + " is "
				+ calClient.invokeAdd(addRequest) + "</h1>";
	}
}
