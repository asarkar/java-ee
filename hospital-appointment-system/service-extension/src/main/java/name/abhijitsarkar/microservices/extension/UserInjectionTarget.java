package name.abhijitsarkar.microservices.extension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserInjectionTarget<X> implements InjectionTarget<X> {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(UserInjectionTarget.class);

    private final InjectionTarget<X> wrapped;
    private List<SimpleImmutableEntry<Method, Field>> producerConsumers;

    public UserInjectionTarget(InjectionTarget<X> wrapped,
	    List<SimpleImmutableEntry<Method, Field>> pc) {
	this.wrapped = wrapped;
	this.producerConsumers = pc;
    }

    @Override
    public void inject(X instance, CreationalContext<X> ctx) {
	wrapped.inject(instance, ctx);

	LOGGER.info("Processing class: {}.", instance.getClass().getName());

	producerConsumers
		.forEach(pc -> {
		    Field field = pc.getValue();

		    LOGGER.info(
			    "Setting the value of field: {} using method: {}.",
			    field.getName(), pc.getKey().getName());

		    field.setAccessible(true);

		    try {
			field.set(instance,
				pc.getKey().invoke(null, new Object[] {}));
		    } catch (SecurityException | IllegalArgumentException
			    | ReflectiveOperationException e) {
			LOGGER.error(
				"An error occurred trying to set the value of field: {}.",
				field.getName(), e);
		    }
		});
    }

    @Override
    public void postConstruct(X instance) {
	wrapped.postConstruct(instance);
    }

    @Override
    public void preDestroy(X instance) {
	wrapped.preDestroy(instance);
    }

    @Override
    public X produce(CreationalContext<X> ctx) {
	return wrapped.produce(ctx);
    }

    @Override
    public void dispose(X instance) {
	wrapped.dispose(instance);
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
	return wrapped.getInjectionPoints();
    }
}
