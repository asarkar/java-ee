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

	private static final JAXWSEngine[] allJAXWSEngines = JAXWSEngine.values();

	public static boolean isActiveJAXWSEngine(JAXWSEngine jaxWsEngine) {
		return getActiveJAXWSEngine().equals(jaxWsEngine);
	}

	public static JAXWSEngine getActiveJAXWSEngine() {
		return getJAXWSEngineByName(activeJAXWSEngine);
	}

	public static JAXWSEngine getJAXWSEngineByName(String jaxWsEngineName) {
		if (jaxWsEngineName != null) {
			for (JAXWSEngine jaxWsEngine : allJAXWSEngines) {
				if (jaxWsEngine.name.equalsIgnoreCase(jaxWsEngineName)) {
					return jaxWsEngine;
				}
			}
		}

		throw new IllegalArgumentException("NO JAX-WS engine found with name "
				+ jaxWsEngineName);
	}
}
