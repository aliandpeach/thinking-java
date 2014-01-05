package chapter01_basic;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-12-30
 */
public class FinallyDemo {
    public static void main(String[] args) {
        System.out.println(gett());
    }

    private static int gett() {
        try {
            return 1;
        } finally {
            return 2;
        }
    }
}
