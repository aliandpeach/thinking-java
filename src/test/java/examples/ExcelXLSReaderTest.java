package examples;

import org.junit.Test;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/12/30
 */
public class ExcelXLSReaderTest {

    @Test
    public void testCheck() throws Exception {
        new ExcelXLSReader(
                "D:\\download\\20151228\\2015.12广东终端维护档案及维护登记表.xls").check();
    }
}