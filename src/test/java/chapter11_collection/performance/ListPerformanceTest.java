package chapter11_collection.performance;

import org.junit.*;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-3
 * Time: 上午11:16
 * ListPerformanceTest
 */
public class ListPerformanceTest {
    @org.junit.Test
    public void testPerformance() throws Exception {
        ListPerformance.performance(new String[]{"10", "5000", "100", "5000",
                "1000", "1000", "10000", "200"});
    }
}
