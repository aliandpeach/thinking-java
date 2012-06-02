package chapter12_exception;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-2
 * Time: 上午11:31
 * TurnOffCheckingTest
 */
public class TurnOffCheckingTest {
    @Test
    public void testTrunOffCheckException() throws Exception {
        TurnOffChecking turnOffChecking = new TurnOffChecking();
        turnOffChecking.trunOffCheckException();
    }
}
