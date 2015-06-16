package name.abhijitsarkar.javaee.spring.customscope;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class MultithreadedRequestScope implements Scope {
    private final ContextManager ctxMgr;

    public MultithreadedRequestScope(BeanFactory beanFactory) {
	ctxMgr = beanFactory.getBean(ContextManager.class);
    }

    @Override
    public Object get(String name, ObjectFactory<?> objFactory) {
	Object obj = ctxMgr.get(name, getConversationId());

	if (obj == null) {
	    obj = objFactory.getObject();
	}

	return obj;
    }

    @Override
    public String getConversationId() {
	return Thread.currentThread().getName();
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
	// TODO Auto-generated method stub
    }

    @Override
    public Object remove(String name) {
	return ctxMgr.remove(name, getConversationId());
    }

    @Override
    public Object resolveContextualObject(String name) {
	return null;
    }
}
