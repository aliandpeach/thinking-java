package chapter13_string;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-2
 * Time: 下午4:42
 * TheReplacementsTest
 */
public class TheReplacementsTest {
    @Test
    public void testRegularReplace() throws Exception {
        TheReplacements theReplacements = new TheReplacements();
        theReplacements.regularReplace("java","java");
    }
}
