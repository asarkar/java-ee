package name.abhijitsarkar.javaee.spring.customscope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "MultithreadedRequestScope")
public class RequestContextImpl implements RequestContext {
    @Autowired
    private UserManager userMgr;

    @Override
    public String getUsername() {
	return userMgr.getUsername();
    }
}
