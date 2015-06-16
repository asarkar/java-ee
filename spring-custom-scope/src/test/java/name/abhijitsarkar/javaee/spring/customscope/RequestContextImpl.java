package name.abhijitsarkar.javaee.spring.customscope;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("MultithreadedRequestScope")
public class RequestContextImpl implements RequestContext {
    @Resource(name = "userManagerImpl1")
    private UserManager userMgr;

    @Override
    public String getUsername() {
	return userMgr.getUsername();
    }
}
