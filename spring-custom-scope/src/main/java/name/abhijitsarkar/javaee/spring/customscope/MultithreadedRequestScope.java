package name.abhijitsarkar.javaee.spring.customscope;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class MultithreadedRequestScope implements Scope {
    public static final String NAME = MultithreadedRequestScope.class
	    .getSimpleName();

    private final BeanFactory beanFactory;

    public MultithreadedRequestScope(BeanFactory beanFactory) {
	System.out.println("MultithreadedRequestScope instantiated.");

	this.beanFactory = beanFactory;
    }

    @Override
    public Object get(String name, ObjectFactory<?> objFactory) {
	System.out.println("MultithreadedRequestScope.get called.");

	Object obj = getContextManager().get(name, getConversationId());

	if (obj == null) {
	    obj = objFactory.getObject();
	}

	return obj;
    }

    private ContextManager getContextManager() {
	return beanFactory.getBean(ContextManager.class);
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
	return getContextManager().remove(name, getConversationId());
    }

    @Override
    public Object resolveContextualObject(String name) {
	return null;
    }
}
