package chapter14_reflect;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class ReflectDemoTest {

	@Test
	public void testShowMethods() {
        ReflectDemo reflectDemo = new ReflectDemo();
        reflectDemo.showMethods(new StringBuilder("a"));
		assertTrue(true);
	}
}
