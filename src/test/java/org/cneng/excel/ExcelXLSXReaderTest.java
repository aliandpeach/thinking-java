package org.cneng.excel;

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
        new ExcelXLSXReader("").check();
    }
}