package name.abhijitsarkar.webservices.jaxws.instrumentation.config;

public enum JAXWSEngine {
	METRO("metro"), CXF("cxf");

	private String name;

	private JAXWSEngine(String name) {
		this.name = name;
	}

	private static final String JAXWS_ENGINE_PROPERTY = "jaxws.engine";

	private static final String activeJAXWSEngine = System
			.getProperty(JAXWS_ENGINE_PROPERTY);

	public static boolean isActiveJAXWSEngine(JAXWSEngine jAXWSEngine) {
		if (activeJAXWSEngine == null) {
			System.out.println("No active deployment profile is found.");

			return false;
		}

		System.out.println("Active JAX-WS engine: " + activeJAXWSEngine);

		return activeJAXWSEngine.equalsIgnoreCase(jAXWSEngine.name);
	}

	public static JAXWSEngine getActiveJAXWSEngine() {
		JAXWSEngine[] allJAXWSEngines = JAXWSEngine.values();

		for (JAXWSEngine jaxWsEngine : allJAXWSEngines) {
			if (jaxWsEngine.name.equalsIgnoreCase(activeJAXWSEngine)) {
				return jaxWsEngine;
			}
		}

		return null;
	}
}
