package examples.quartz.actions;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-6
 * Time: 下午3:54
 * TestCommon
 */
public class TestCommon {
    public static void main(String[] args) {
        calcDouble();

        int kkk = 2 ^ 32;
        System.out.println(Math.pow((int) 2, (int) 16));
    }

    private static void calcDouble() {
        double a = (4 * 0.55 + 4 * 0.15 + 3 * 0.15 + 4 * 0.05 + 3 * 0.05 + 4 * 0.05) * 0.8;
        double b = (4 * 0.2 + 3 * 0.2 + 4 * 0.2 + 3 * 0.2 + 5 * 0.2) * 0.2;
        BigDecimal bg = new BigDecimal(a + b);
        System.out.println(a);
        System.out.println(b);
        System.out.println(bg);
    }
}
