package name.abhijitsarkar.javaee.spring.customscope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Client {
    @Autowired
    private RequestContext reqCtx;

    public String getUsername() {
	return reqCtx.getUsername();
    }
}
