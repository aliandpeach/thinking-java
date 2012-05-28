package chapter06_basic;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BasicDemoTest {

	@Test
	public void testBinaryString() {
        BasicDemo basic = new BasicDemo();
        basic.binaryString();
		assertTrue(true);
	}
    @Test
	public void testLabeledFor() {
        BasicDemo basic = new BasicDemo();
        basic.labeledFor();
		assertTrue(true);
	}

}
