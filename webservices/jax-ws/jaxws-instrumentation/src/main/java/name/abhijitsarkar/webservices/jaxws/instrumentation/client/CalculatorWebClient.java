package name.abhijitsarkar.webservices.jaxws.instrumentation.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CalculatorWebClient {
	@Autowired
	CalculatorClient calClient;

	// TODO: Use @ModelAttribute
	@RequestMapping(value = "/client", method = RequestMethod.GET)
	public String add(HttpServletRequest request, HttpServletResponse response) {
		int arg0 = Integer.parseInt(request.getParameter("arg0"));
		int arg1 = Integer.parseInt(request.getParameter("arg1"));

		request.setAttribute("message", "Sum of " + arg0 + " and " + arg1
				+ " is " + calClient.invokeAdd(arg0, arg1));

		return "result";
	}
}
