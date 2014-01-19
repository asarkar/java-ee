package name.abhijitsarkar.webservices.jaxws.instrumentation.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.soap.SOAPBinding;

import name.abhijitsarkar.webservices.jaxws.handler.SOAPMessageLoggingHandler;
import name.abhijitsarkar.webservices.jaxws.instrumentation.client.CalculatorClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "name.abhijitsarkar.webservices.jaxws.instrumentation.config", excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DispatcherConfig.class))
public class AppConfig {
	private static final String ENDPOINT_URL = "http://localhost:8080/jaxws-instrumentation/service/CalculatorService";
	public static final String NAMESPACE_URI = "http://instrumentation.jaxws.webservices.abhijitsarkar.name/";
	private static final String SERVICE_NAME = "CalculatorService";
	private static final String PORT_NAME = "CalculatorPort";
	private static final QName serviceName = new QName(NAMESPACE_URI,
			SERVICE_NAME);
	private static final QName portName = new QName(NAMESPACE_URI, PORT_NAME);

	@Bean
	CalculatorClient rmsClient() {
		final QName portQName = new QName(NAMESPACE_URI, PORT_NAME);
		return new CalculatorClient(jaxWsService(), portQName);
	}

	@Bean
	Service jaxWsService() {
		Service service = Service.create(serviceName);
		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, ENDPOINT_URL);

		service.setHandlerResolver(handlerResolver());

		return service;
	}

	private HandlerResolver handlerResolver() {
		return new HandlerResolver() {

			@SuppressWarnings("rawtypes")
			@Override
			public List<Handler> getHandlerChain(PortInfo arg0) {
				List<Handler> handlerChain = new ArrayList<Handler>();
				handlerChain.add(new SOAPMessageLoggingHandler());

				return handlerChain;
			}
		};
	}
}
