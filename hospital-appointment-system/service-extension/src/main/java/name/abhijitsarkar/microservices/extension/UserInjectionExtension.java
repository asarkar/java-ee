package name.abhijitsarkar.microservices.extension;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessInjectionTarget;

public class UserInjectionExtension implements Extension {
    /*
     * The @WithAnnotations annotation causes the container to deliver the
     * ProcessAnnotatedType events only for the types which contain the
     * specified annotation.
     */
    <T> void initUsers(
	    @Observes ProcessInjectionTarget<T> pit) {
	AnnotatedType<T> at = pit.getAnnotatedType();

	// Check if there is any method defined as Property Resolver
	for (AnnotatedMethod<? super T> method : at.getMethods()) {
	    if (method.isAnnotationPresent(Users.class)) {
		
	    }
	}
    }
}
