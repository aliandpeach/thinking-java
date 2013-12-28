package chapter16_array;

import chapter01_basic.ArrayDemo;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ArrayDemoTest {

    @Test
    public void testBasic() {
        ArrayDemo arrayDemo = new ArrayDemo();
        arrayDemo.basic();
        assertTrue(true);
    }

    @Test
    public void testCopyArray() throws Exception {
        ArrayDemo arrayDemo = new ArrayDemo();
        arrayDemo.copyArray();
    }

    @Test
    public void testCompare() throws Exception {
        ArrayDemo arrayDemo = new ArrayDemo();
        arrayDemo.compare();
    }

    @Test
    public void testSort() throws Exception {
        ArrayDemo arrayDemo = new ArrayDemo();
        arrayDemo.sort();
    }
}
