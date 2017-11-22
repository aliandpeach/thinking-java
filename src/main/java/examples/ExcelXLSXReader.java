package examples;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellType.*;

/**
 * excel读取
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/6/5
 */
public class ExcelXLSXReader {
    private static final Logger _log = LoggerFactory.getLogger(ExcelXLSXReader.class);
    private XSSFWorkbook wb;
    private FormulaEvaluator formulaEvaluator = null;

    public ExcelXLSXReader(String filename) throws Exception {
        wb = new XSSFWorkbook(new FileInputStream(filename));
        formulaEvaluator = new XSSFFormulaEvaluator(wb);
    }

    public List<String> check() throws Exception {
        List<String> result = new ArrayList<>();
        try {
            // 读取第一章表格内容
            XSSFSheet sheet = wb.getSheetAt(0);
            // 定义 row、cell
            // 循环输出表格中的内容
            for (Row row : sheet) {
                for (Cell cell : row) {
                    //结果比较
                    System.out.print(getCellValue(cell) + "==VS==" + getCellValueFormula(cell, formulaEvaluator) + "\t");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("已运行xlRead() : " + e);
        }
        return result;
    }

    //未处理公式
    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getRichStringCellValue().getString().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //非线程安全
                    return sdf.format(cell.getDateCellValue());
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    //处理公式
    public static String getCellValueFormula(Cell cell, FormulaEvaluator formulaEvaluator) {
        if (cell == null || formulaEvaluator == null) {
            return null;
        }

        if (cell.getCellTypeEnum() == FORMULA) {
            return String.valueOf(formulaEvaluator.evaluate(cell).getNumberValue());
        }
        return getCellValue(cell);
    }

}
