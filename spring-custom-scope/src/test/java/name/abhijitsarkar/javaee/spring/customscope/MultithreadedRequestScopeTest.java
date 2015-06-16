package name.abhijitsarkar.javaee.spring.customscope;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MultithreadedRequestScopedBeansProcessor.class)
public class MultithreadedRequestScopeTest {
    @Autowired
    private Client client;
    
    @Autowired
    private HttpServletRequest httpReq;

    @Test
    public void testUsername() {
	System.out.println(client.getUsername());
	System.out.println(httpReq.getLocalName());
    }
}
