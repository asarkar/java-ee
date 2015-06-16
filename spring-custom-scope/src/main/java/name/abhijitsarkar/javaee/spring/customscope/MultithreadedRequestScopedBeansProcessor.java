package name.abhijitsarkar.javaee.spring.customscope;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
public class MultithreadedRequestScopedBeansProcessor implements
	BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanFactory(
	    ConfigurableListableBeanFactory beanFactory) throws BeansException {
	Scope multithreadedRequestScope = new MultithreadedRequestScope(
		beanFactory);

	beanFactory.registerScope(multithreadedRequestScope.getClass()
		.getSimpleName(), multithreadedRequestScope);

    }

    @Override
    public void postProcessBeanDefinitionRegistry(
	    BeanDefinitionRegistry registry) throws BeansException {
	// TODO Auto-generated method stub

    }
}
