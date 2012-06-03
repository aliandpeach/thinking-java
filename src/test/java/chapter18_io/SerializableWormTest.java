package chapter18_io;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-3
 * Time: 下午5:17
 * SerializableWormTest
 */
public class SerializableWormTest {
    @Test
    public void testWorm() throws Exception {
        SerializableWorm worm = new SerializableWorm();
        worm.worm();
    }
}
