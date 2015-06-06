package org.cneng.httpclient;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * EXCEL文件读写工具类
 * <b>版权信息 :</b> 2014，广州云宏信息技术有限公司<br/>
 * <b>功能描述 :</b> <br/>
 * <b>版本历史 :</b> <br/>
 *
 * @author wanrui| 2015年3月12日 上午10:39:54 | 创建
 */
public class ExcelUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容，若为字符串的要加单引号
     */
    public static String getString(FormulaEvaluator evaluator, Row row, int column) {
        // String columnLetter = CellReference.convertNumToColString(column);
        String strCell = "";
        try {
            if (row == null) return strCell;
            Cell cell = row.getCell(column);
            if (cell == null) return strCell;
            switch (cell.getCellType()) {
                // CELL_TYPE_FORMULA will never happen
                case Cell.CELL_TYPE_FORMULA:
                    CellValue cellValue = evaluator.evaluate(cell);
                    if (cellValue == null) return strCell;
                    strCell = cellValue.getStringValue();
                    break;
                default:
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    strCell = cell.getStringCellValue();
                    break;
            }
        } catch (Exception e) {
            logger.error("Excel数据解析错误", e);
        }
        return strCell;
    }

    public static Date getDate(FormulaEvaluator evaluator, Row row, int column) {
        // String columnLetter = CellReference.convertNumToColString(column);
        try {
            Cell cell = row.getCell(column);
            CellValue cellValue = evaluator.evaluate(cell);
            if (cellValue == null) return null;
            if (cellValue.getCellType() == Cell.CELL_TYPE_STRING) {
                if ("无".equals(cell.getStringCellValue())
                        || StringUtil.isBlank(cell.getStringCellValue())) return null;
            }
            return cell.getDateCellValue();
        } catch (Exception e) {
            logger.error("日期格式错误", e);
        }
        return null;
    }
}
