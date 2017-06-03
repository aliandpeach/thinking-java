package org.cneng.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.cneng.httpclient.QueryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.cneng.httpclient.ExcelUtil.getString;

/**
 * 检查一份excel企业名单表
 * 对于红色字体的部分检查该企业是否已经被注销
 * 对于正常字体部分检查该企业的资料是否正确填写
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/6/5
 */
public class ExcelXLSXReader {
    private static final Logger _log = LoggerFactory.getLogger(ExcelXLSXReader.class);
    private Workbook wb;
    FormulaEvaluator evaluator;
    private QueryManager queryManager;

    public ExcelXLSXReader(String filename) throws Exception {
        wb = new SXSSFWorkbook(new XSSFWorkbook(new FileInputStream(filename)), 500);
        evaluator = wb.getCreationHelper().createFormulaEvaluator();
        queryManager = QueryManager.getInstance();
    }

    public List<String> check() throws Exception {
        List<String> result = new ArrayList<String>();
        try {
            //读取第一张表
            Sheet sheet = wb.getSheet("惠州vip");
            //得到总行数
            int rowNum = sheet.getLastRowNum();
            _log.info("excel表格总行数为：" + rowNum);
            Row row = sheet.getRow(5);
            // 专卖证号
            String tobacco = getString(evaluator, row, 3);
            // 联系电话
            String phone = getString(evaluator, row, 6);
            System.out.println("tobacco=" + tobacco + ", phone=" + phone);
        } finally {
            wb.close();
        }
        return result;
    }

}
