package chapter01_basic;


import chapter01_basic.Arith;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-5
 * Time: 下午5:13
 * ArithTest
 */
public class ArithTest {
    @Test
    public void testAdd() throws Exception {
        System.out.println(Arith.add(11.12, 12.14));
    }
    @Test
    public void testSub() throws Exception {
        System.out.println(Arith.sub(11.12, 12.14));
    }
    @Test
    public void testMul() throws Exception {
        System.out.println(Arith.mul(11.12, 12.14));
    }
    @Test
    public void testDiv1() throws Exception {
        System.out.println(Arith.div(11.12, 12.14));
    }
    @Test
    public void testDiv2() throws Exception {
        System.out.println(Arith.div(12.19, 12.17, 6));
    }
    @Test
    public void testRound() throws Exception {
        System.out.println(Arith.round(11.12, 1));
    }
}
