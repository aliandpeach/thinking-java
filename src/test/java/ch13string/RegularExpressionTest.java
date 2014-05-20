package ch13string;

import org.junit.Before;
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
    RegularExpression regularExpression;

    @Before
    public void before() {
        regularExpression = new RegularExpression();
    }
    @Test
    public void testMatches() throws Exception {
        boolean isMatch = regularExpression.matches("java","java");
        assertThat(isMatch, is(true));
    }

    @Test
    public void testLookingAt() throws Exception {
        assertThat(regularExpression.lookingAt("java","java"), is(true));
    }

    @Test
    public void testFind() throws Exception {
        regularExpression.find("java","java");
    }

    @Test
    public void testGroup() throws Exception {
        regularExpression.group("java","java");
    }
}
