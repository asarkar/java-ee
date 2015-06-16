package name.abhijitsarkar.javaee.spring.customscope;

import org.springframework.stereotype.Component;

@Component
public class UserManagerImpl implements UserManager {
    @Override
    public String getUsername() {
	return "asarkar";
    }
}
