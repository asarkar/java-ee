package name.abhijitsarkar.javaee.microservices.salon.user.repository;

import static com.google.common.base.Preconditions.checkArgument;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import name.abhijitsarkar.javaee.microservices.salon.user.domain.PhoneNumberConverter;
import name.abhijitsarkar.javaee.microservices.salon.user.domain.User;

@RepositoryEventHandler(User.class)
@Component
public class UserEventHandler {
	private PhoneNumberConverter converter = new PhoneNumberConverter();

	@HandleBeforeCreate
	@HandleBeforeSave
	public void beforeCreatingUser(User user) {
		String cleansedPhNum = converter.convertToDatabaseColumn(user.getPhoneNum());

		checkArgument(cleansedPhNum.length() == 10, "Phone number must have exactly 10 digits. %s", user.getPhoneNum());
	}
}
