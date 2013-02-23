package edu.certification.abhijitsarkar.ocewsd.wssecurity.calculatorua.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;

import edu.certification.abhijitsarkar.ocewsd.wssecurity.calculatorua.CalculatorWS;
import edu.certification.abhijitsarkar.ocewsd.wssecurity.calculatorua.CalculatorWS_Service;

/**
 * 
 * @author Abhijit
 */
@WebServlet(name = "ClientServlet", urlPatterns = { "/ClientServlet" })
public class ClientServlet extends HttpServlet {
	static {
		// sunjce_provider.jar added to glassfish/domains/domain1/lib/ext
		// required granting permission to
		// ${com.sun.aas.instanceRoot}/config/server.policy
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

	@WebServiceRef(wsdlLocation = "http://localhost:8080/calculator-ua/CalculatorWS?wsdl")
	private CalculatorWS_Service service;

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			/*
			 * TODO output your page here. You may use following sample code.
			 */
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet ClientServlet</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet ClientServlet at "
					+ request.getContextPath() + "</h1>");

			try { // Call Web Service Operation
				CalculatorWS port = service.getCalculatorWSPort();
				int i = 3;
				int j = 4;
				int result = port.add(i, j);
				out.println("Result = " + result);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			out.println("</body>");
			out.println("</html>");
		} finally {
			out.close();
		}
	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * 
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}
}
