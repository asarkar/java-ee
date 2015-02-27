package name.abhijitsarkar.microservices.extension;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import name.abhijitsarkar.microservices.user.AbstractUser;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ FIELD })
public @interface Users {
    Class<? extends AbstractUser> value();
}
