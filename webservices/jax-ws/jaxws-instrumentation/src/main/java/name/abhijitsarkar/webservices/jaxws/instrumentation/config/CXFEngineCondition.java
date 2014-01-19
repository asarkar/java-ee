package name.abhijitsarkar.webservices.jaxws.instrumentation.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class CXFEngineCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context,
			AnnotatedTypeMetadata metadata) {
		boolean isConditionalOnCXFEngine = metadata
				.isAnnotated(ConditionalOnCXFEngine.class.getName());

		return isConditionalOnCXFEngine && isCXFEngineActive();
	}

	private boolean isCXFEngineActive() {
		return JAXWSEngine.isActiveJAXWSEngine(JAXWSEngine.CXF);
	}
}
