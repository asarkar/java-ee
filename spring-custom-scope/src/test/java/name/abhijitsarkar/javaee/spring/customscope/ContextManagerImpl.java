package name.abhijitsarkar.javaee.spring.customscope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContextManagerImpl implements ContextManager {
    @Autowired
    private UserManager userMgr;

    private Map<String, RequestContext> reqCtxMap;

    public ContextManagerImpl() {
	System.out.println("ContextManagerImpl instantiated.");
    }

    @PostConstruct
    void init() {
	System.out.println("Inside ContextManagerImpl init.");

	String conversationId = Thread.currentThread().getName();

	reqCtxMap = new ConcurrentHashMap<>();
	reqCtxMap.put(conversationId, new RequestContextImpl(userMgr));

	ThreadAttributes attr = new ThreadAttributes();
	attr.setConversationId(conversationId);
	ThreadContextHolder.setThreadAttributes(attr);

	System.out.println("Cached bean for conversation id: "
		+ getConversationId());
    }

    @Override
    public Object get(String name, String conversationId) {
	System.out.println("Retrieving bean with name: "
		+ name
		+ " for conversation id: "
		+ ThreadContextHolder.currentThreadAttributes()
			.getConversationId());

	return reqCtxMap.get(getConversationId());
    }

    public String getConversationId() {
	return ThreadContextHolder.currentThreadAttributes()
		.getConversationId();
    }

    @Override
    public Object remove(String name, String conversationId) {
	// TODO Auto-generated method stub
	return null;
    }
}
