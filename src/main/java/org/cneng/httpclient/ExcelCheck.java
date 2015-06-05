package org.cneng.httpclient;

import ch19enums.Input;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 检查一份excel企业名单表
 * 对于红色字体的部分检查该企业是否已经被注销
 * 对于正常字体部分检查该企业的资料是否正确填写
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/6/5
 */
public class ExcelCheck {
    private static final Logger _log = LoggerFactory.getLogger(ExcelCheck.class);
    private Workbook wb;
    private QueryManager queryManager;
    public ExcelCheck(String filename) throws Exception {
        wb = new SXSSFWorkbook(new XSSFWorkbook(new FileInputStream(filename)), 500);
        queryManager = QueryManager.getInstance();
    }

    public List<String> check() {
        List<String> result = new ArrayList<String>();
        //读取第一张表
        Sheet sheet = wb.getSheetAt(0);
        //得到总行数
        int rowNum = sheet.getLastRowNum();
        _log.info("excel表格总行数为：" + rowNum);

        for (int i = 1; i <= rowNum; i++) {
            Row row = sheet.getRow(i);

            // 演示判断为红色
//            if(IndexedColors.RED.index == wb.getFontAt(wCell.getCellStyle()
//                    .getFontIndex())
//                    .getColor()){
        }

        return result;
    }

}
