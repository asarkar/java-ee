package name.abhijitsarkar.javaee.spring.customscope;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MultithreadedRequestScopedBeansProcessor.class)
public class MultithreadedRequestScopeTest {
    @Autowired
    private RequestContext reqCtx;

    @Test
    public void testUsername() {
	System.out.println(reqCtx.getUsername());
    }
}
