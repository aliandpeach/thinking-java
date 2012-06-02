package chapter12_exception;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ExceptionDemoTest {

    @Test
    public void testExceptionChain() throws Exception {
        ExceptionDemo exceptionDemo = new ExceptionDemo();
        exceptionDemo.exceptionChain();
    }
}
