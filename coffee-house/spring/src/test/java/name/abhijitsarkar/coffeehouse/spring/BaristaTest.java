package name.abhijitsarkar.coffeehouse.spring;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Abhijit Sarkar
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class BaristaTest {

    @Autowired
    private Barista barista;

    @Before
    public void setUp() {
        Assert.assertNotNull(barista);
    }

    @Test
    public void testMenu() {
        Assert.assertNotNull(barista.menu());

        Assert.assertNull(barista.menu().getBlends());
        Assert.assertNull(barista.menu().getFlavors());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testServeByBlend() {
        barista.serve("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testServeByBlendAndFlavor() {
        barista.serve("", "");
    }
}
