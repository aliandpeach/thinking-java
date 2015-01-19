import net.mindview.util.CountingGenerator;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 14-3-24
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String str = "&nbsp;&nbsp;";
        System.out.println(str.replace("&nbsp;", ""));
    }

    private static boolean checkEmail(String email) {
        // 先将字符串变成字符数组
        char[] emailChars = email.toCharArray();
        for (char c : emailChars) {
            if ((c < 'A' || (c > 'Z' && c < 'a') || (c > 'z')) && c != '.' && c != '@') {
                return false;
            }
        }
        // 合法字符：
        return true;
    }

    private static int countArray(int[] arr) {
        int halfLen = arr.length / 2;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i : arr) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
                if (map.get(i) > halfLen) {
                    return 1;
                }
            } else {

                map.put(i, 1);
            }
        }
        return -1;
    }


    private static int countArray2(int[] arr) {
        int max = Integer.MAX_VALUE / 10000;
        System.out.println(Integer.MAX_VALUE);
        int halflen = arr.length / 2;
        int[] all = new int[max];
        for (int i : arr) {
            all[arr[i] < 0 ? max / 2 - arr[i] : arr[i]] += 1;
            if (all[arr[i]] > halflen) return 1;
        }
        return -1;
    }

    private static boolean papapa(String str) {
        Set<Character> left = new HashSet<Character>() {{
            add('{');
            add('[');
            add('(');
        }};
        Map<Character, Integer> match = new HashMap<Character, Integer>() {{
            put('{', 1);
            put('}', 1);
            put('[', 2);
            put(']', 2);
            put('(', 3);
            put(')', 3);
        }};
        Stack<Character> stack = new Stack<Character>();
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (left.contains(c)) stack.push(c);
            else {
                char cc = stack.pop();
                if (!match.get(cc).equals(match.get(c))) return false;
            }
        }
        return true;
    }

}
