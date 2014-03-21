package ch13string;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-12-29
 */
public class Intern {
    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "world";
        String s3 = "helloworld";
        s1 = (s1 + s2).intern();
        System.out.println(s1 == s3);

    }
}
