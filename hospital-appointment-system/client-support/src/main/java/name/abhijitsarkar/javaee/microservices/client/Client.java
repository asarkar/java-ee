package name.abhijitsarkar.javaee.microservices.client;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Qualifier
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface Client {

}
