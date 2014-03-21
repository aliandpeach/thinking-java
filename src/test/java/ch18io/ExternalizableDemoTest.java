package ch18io;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-3
 * Time: 下午6:33
 * ExternalizableDemoTest
 */
public class ExternalizableDemoTest {
    @Test
    public void testSerial() throws Exception {
        ExternalizableDemo demo = new ExternalizableDemo();
        demo.serial();
    }
}
