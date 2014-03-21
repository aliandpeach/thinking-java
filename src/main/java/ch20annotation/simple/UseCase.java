package ch20annotation.simple;

import java.util.regex.Pattern;

/**
 * 用法举例.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 14-3-21
 */
public class UseCase {
    @Regex(regexRule = RegexRule.EMAIL)
    public void regexEmail(String unCheckedString, String regexRule) {
        boolean rs = Pattern.compile(regexRule).matcher(unCheckedString).find();
        System.out.println(rs);
    }
}
