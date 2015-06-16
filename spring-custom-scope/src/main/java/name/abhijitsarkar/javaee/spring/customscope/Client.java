package name.abhijitsarkar.javaee.spring.customscope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Client {
    @Autowired
    private RequestContext reqCtx;

    public Client() {
	System.out.println("Client instantiated.");
    }

    public String getUsername() {
	return reqCtx.getUsername();
    }
}
