package ch18io;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-12-31
 */
public class FormatOutput {
    public static void main(String[] args) {
        int count = 0;
        for (int ch = 'a'; ch <= 'z'; ch++) {
            System.out.printf("%1$4c%1$4x", ch);
            if (++count % 6 == 0) {
                System.out.printf("%n");
            }
        }
    }
}
