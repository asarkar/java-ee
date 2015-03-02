package name.abhijitsarkar.microservices.extension;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import name.abhijitsarkar.microservices.user.AbstractUser;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ METHOD })
public @interface Produces {
    Class<? extends AbstractUser> value();
}
