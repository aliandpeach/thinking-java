package ch13string;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-2
 * Time: 下午4:49
 * ThreatAnalyzerTest
 */
public class ThreatAnalyzerTest {
    @Test
    public void testRegexScan() throws Exception {
        ThreatAnalyzer threatAnalyzer = new ThreatAnalyzer();
        threatAnalyzer.regexScan();
    }
}
