package chapter18_io;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-3
 * Time: 下午4:50
 * BasicFileOutputTest
 */
public class BasicFileOutputTest {
    @Test
    public void testBasicWriteFile() throws Exception {
        BasicFileOutput basicOut = new BasicFileOutput();
        basicOut.basicWriteFile();
    }
}
