package chapter13_string;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-2
 * Time: 下午4:28
 * RegularExpressionTest
 */
public class RegularExpressionTest {
    @Test
    public void testMatches() throws Exception {
        RegularExpression regularExpression = new RegularExpression();
        boolean isMatch = regularExpression.matches("java","java");
        assertThat(isMatch, is(true));
    }

    @Test
    public void testLookingAt() throws Exception {
        RegularExpression regularExpression = new RegularExpression();
        assertThat(regularExpression.lookingAt("java","java"), is(true));
    }

    @Test
    public void testFind() throws Exception {
        RegularExpression regularExpression = new RegularExpression();
        regularExpression.find("java","java");
    }

    @Test
    public void testGroup() throws Exception {
        RegularExpression regularExpression = new RegularExpression();
        regularExpression.group("java","java");
    }
}
