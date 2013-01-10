package chapter06_basic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BasicDemoTest {

	@Test
	public void testBinaryString() {
        BasicDemo basic = new BasicDemo();
        basic.binaryString();
		assertThat(true, is(true));
	}
    @Test
	public void testLabeledFor() {
        BasicDemo basic = new BasicDemo();
        basic.labeledFor();
		assertTrue(true);
	}

    /**
     * 测试Class对象是什么东东
     */
    @Test
    public void testWhatClass(){
        BasicDemo basicDemo = new BasicDemo();
        assertThat(basicDemo.whatClass(""), is(true));
    }

}
