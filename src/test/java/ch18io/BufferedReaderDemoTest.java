package ch18io;

import org.junit.Test;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-3
 * Time: 下午4:04
 * BufferedReaderDemoTest
 */
public class BufferedReaderDemoTest {
    @Test
    public void testRead() throws Exception {
        BufferedReaderDemo demo = new BufferedReaderDemo();
        String fileName =this.getClass().getClassLoader().getResource("").getPath();
        fileName = fileName.substring(0, fileName.lastIndexOf("/test-classes/"));
        fileName = fileName + "/classes/datasource.properties";
        System.out.println(demo.read(fileName));
        System.out.println(new File(".").getCanonicalPath());
    }

    @Test
    public void testStringReader() throws Exception {
        BufferedReaderDemo demo = new BufferedReaderDemo();
        String fileName =this.getClass().getClassLoader().getResource("").getPath();
        fileName = fileName.substring(0, fileName.lastIndexOf("/test-classes/"));
        fileName = fileName + "/classes/datasource.properties";
        demo.stringReader(fileName);
    }
}
