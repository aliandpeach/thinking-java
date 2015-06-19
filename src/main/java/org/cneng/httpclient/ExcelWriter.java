package org.cneng.httpclient;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.cneng.httpclient.ExcelUtil.getDate;
import static org.cneng.httpclient.ExcelUtil.getString;

/**
 * 根据企业名单自动抓取信息并生成Excel文件
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/6/11
 */
public class ExcelWriter {
    private static final Logger _log = LoggerFactory.getLogger(ExcelWriter.class);
    private Workbook wb;

    public ExcelWriter(String filename) throws Exception {
        wb = new SXSSFWorkbook(new XSSFWorkbook(new FileInputStream(filename)), 500);
    }

    public List<String> check() throws Exception {
        List<String> result = new ArrayList<String>();
        try {
            //读取第一张表
            Sheet sheet = wb.getSheetAt(0);
            //得到总行数
            int rowNum = sheet.getLastRowNum();
            _log.info("excel表格总行数为：" + rowNum);

            for (int i = 1; i <= rowNum; i++) {
                Row row = sheet.getRow(i);
                // 判断字体颜色是否为红色
                CellStyle cellStyle = row.getCell(0).getCellStyle();
                if (IndexedColors.RED.index == wb.getFontAt(cellStyle
                        .getFontIndex()).getColor()) {
                    // 登录工商局网站，通过验证码强行破解后查询企业信息

                } else {
                    // 直接通过后面的链接查询企业信息

                }
            }

        } finally {
            wb.close();
        }
        return result;
    }

}
