package ch13string;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-2
 * Time: 下午3:43
 * java的正则表达式
 */
public class RegularExpression {
    /**
     * 匹配整个字符串
     *
     * @param regex
     * @param str
     * @return
     */
    public boolean matches(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 匹配字符串的开始部分
     *
     * @param regex
     * @param str
     * @return
     */
    public boolean lookingAt(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.lookingAt();
    }

    /**
     * 查找多个匹配
     *
     * @param regex
     * @param str
     * @return
     */
    public List<String> find(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        List<String> result = new ArrayList<String>();
        while (matcher.find()) {
            result.add("group->" + matcher.group()
                    + "\nstart->" + matcher.start()
                    + "\nend->" + matcher.end() + "\n");
        }
        return result;
    }

    /**
     * 组是用括号划分的正则表达式regex
     * 比如A(B(C))D：组0是ABCD，组1是BC，组2是C
     *
     * @param regex
     * @param str
     * @return
     */
    public List<String> group(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        List<String> result = new ArrayList<String>();
        return result;
    }
}
