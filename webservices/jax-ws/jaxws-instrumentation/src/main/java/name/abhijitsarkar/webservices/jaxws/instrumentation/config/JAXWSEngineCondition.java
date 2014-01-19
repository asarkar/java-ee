package name.abhijitsarkar.webservices.jaxws.instrumentation.config;

import java.util.Map;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class JAXWSEngineCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context,
			AnnotatedTypeMetadata metadata) {
		boolean isConditionalOnJAXWSEngine = metadata
				.isAnnotated(ConditionalOnJAXWSEngine.class.getName());

		if (!isConditionalOnJAXWSEngine) {
			return false;
		}

		Map<String, Object> attributes = metadata
				.getAnnotationAttributes(ConditionalOnJAXWSEngine.class
						.getName());
		String jaxWsEngine = (String) attributes.get("value");

		return isRequestedEngineActive(jaxWsEngine);
	}

	private boolean isRequestedEngineActive(String jaxWsEngine) {
		return JAXWSEngine.isActiveJAXWSEngine(JAXWSEngine
				.getJAXWSEngineByName(jaxWsEngine));
	}
}
