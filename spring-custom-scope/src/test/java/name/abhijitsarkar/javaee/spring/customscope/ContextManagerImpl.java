package name.abhijitsarkar.javaee.spring.customscope;

import org.springframework.stereotype.Component;

@Component
public class ContextManagerImpl implements ContextManager {
    @Override
    public Object get(String name, String conversationId) {
	System.out.println("Retrieving bean with name: " + name);

	return new RequestContext() {
	    @Override
	    public String getUsername() {
		return "Abhijit Sarkar";
	    }
	};
    }

    @Override
    public Object remove(String name, String conversationId) {
	// TODO Auto-generated method stub
	return null;
    }
}
