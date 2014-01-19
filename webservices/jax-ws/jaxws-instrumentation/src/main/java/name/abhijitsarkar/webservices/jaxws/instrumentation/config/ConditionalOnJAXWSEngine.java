package name.abhijitsarkar.webservices.jaxws.instrumentation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Conditional(JAXWSEngineCondition.class)
public @interface ConditionalOnJAXWSEngine {
	public String value();
}