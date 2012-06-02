package chapter14_reflect;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-2
 * Time: 下午7:08
 * SimpleDynamicProxyTest
 */
public class SimpleDynamicProxyTest {
    @Test
    public void testSimpleProxy() throws Exception {
        SimpleDynamicProxy simpleDynamicProxy = new SimpleDynamicProxy();
        simpleDynamicProxy.simpleProxy();
    }
}
