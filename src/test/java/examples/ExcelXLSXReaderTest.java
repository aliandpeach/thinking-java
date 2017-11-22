package examples;

import org.junit.Test;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/12/30
 */
public class ExcelXLSXReaderTest {

    @Test
    public void testCheck() throws Exception {
        new ExcelXLSXReader("E:\\download\\云彩付文档汇总\\zzz.xlsx").check();
    }
    @Test
    public void testCheck1() throws Exception {
        System.out.println(String.format("%.2f", Double.valueOf("236.000052")));
        System.out.println(String.format("%.2f", Double.valueOf("236")));
        System.out.println(String.format("%.0f", Double.valueOf("236.0")));
        System.out.println(String.format("%.2f", Double.valueOf("236.09")));
        System.out.println(String.format("%.2f", Double.valueOf("236.0")));
    }
}