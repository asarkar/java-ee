package name.abhijitsarkar.microservices.extension;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.WithAnnotations;

import name.abhijitsarkar.microservices.user.AbstractUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserInjectionExtension implements Extension {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(UserInjectionExtension.class);

    private Map<Class<?>, Map<Class<?>, ProducerConsumers>> producerConsumersMap = new HashMap<>();

    public static class ProducerConsumers {
	private final Method producer;
	private List<Field> consumers;

	private ProducerConsumers(Method producer) {
	    this.producer = producer;
	}

	private void addConsumer(Field consumer) {
	    if (this.consumers == null) {
		this.consumers = new ArrayList<>();
	    }

	    this.consumers.add(consumer);
	}

	public Method getProducer() {
	    return producer;
	}

	public List<Field> getConsumers() {
	    return Collections.unmodifiableList(consumers);
	}
    }

    /*
     * The @WithAnnotations annotation causes the container to deliver the
     * ProcessAnnotatedType events only for the types which contain the
     * specified annotation.
     */
    <X> void processAnnotatedType(@Observes @WithAnnotations({ Produces.class,
	    Consumes.class }) ProcessAnnotatedType<X> pat) {
	AnnotatedType<X> at = pat.getAnnotatedType();

	at.getMethods()
		.stream()
		.filter(method -> method.isAnnotationPresent(Produces.class))
		.forEach(
			method -> {
			    Class<? extends AbstractUser> injectionType = method
				    .getAnnotation(Produces.class).value();

			    producerConsumersMap
				    .compute(
					    injectionType,
					    (key, map) -> {
						if (map != null) {
						    throw new TooManyProducersException(
							    "More than one producer found for type: "
								    + injectionType);
						}

						Map<Class<?>, ProducerConsumers> m = new HashMap<>();
						m.put(null,
							new ProducerConsumers(
								method.getJavaMember()));

						return m;
					    });
			});

	at.getFields()
		.stream()
		.filter(field -> field.isAnnotationPresent(Consumes.class))
		.forEach(
			field -> {
			    Class<? extends AbstractUser> injectionType = field
				    .getAnnotation(Consumes.class).value();

			    producerConsumersMap.compute(injectionType, (key,
				    map) -> {
				if (map == null) {
				    throw new NoSuchProducerException(
					    "No producer found for type: "
						    + injectionType);
				}

				map.compute(at.getClass(), (clazz,
					producerConsumers) -> {
				    producerConsumers.addConsumer(field
					    .getJavaMember());

				    return producerConsumers;
				});

				return map;
			    });
			});
    }

    <X> void processInjectionTarget(@Observes ProcessInjectionTarget<X> pit) {
	AnnotatedType<X> at = pit.getAnnotatedType();

	List<ProducerConsumers> producerConsumers = at
		.getFields()
		.stream()
		.filter(field -> field.isAnnotationPresent(Consumes.class))
		.map(field -> producerConsumersMap.get(
			field.getAnnotation(Consumes.class).value()).get(
			at.getClass())).filter(pc -> pc != null)
		.collect(Collectors.toList());

	if (producerConsumers != null && !producerConsumers.isEmpty()) {
	    pit.setInjectionTarget(new UserInjectionTarget<X>(pit
		    .getInjectionTarget(), producerConsumers));
	}
    }
}
