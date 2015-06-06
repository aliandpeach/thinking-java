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
import java.util.Date;
import java.util.List;

import static org.cneng.httpclient.ExcelUtil.getDate;
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
public class ExcelCheck {
    private static final Logger _log = LoggerFactory.getLogger(ExcelCheck.class);
    private Workbook wb;
    FormulaEvaluator evaluator;
    private QueryManager queryManager;

    public ExcelCheck(String filename) throws Exception {
        wb = new SXSSFWorkbook(new XSSFWorkbook(new FileInputStream(filename)), 500);
        evaluator = wb.getCreationHelper().createFormulaEvaluator();
        queryManager = QueryManager.getInstance();
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
                // 企业名称
                String companyName = getString(evaluator, row, 0);
                // 注册号
                String taxno = getString(evaluator, row, 1);
                // 法定代表人
                String lawPerson = getString(evaluator, row, 2);
                // 成立日期
                Date regDate = getDate(evaluator, row, 3);
                // 住所
                String location = getString(evaluator, row, 4);
                // 经营范围
                String business = getString(evaluator, row, 5);
                // 股东/发起人
                String stockholder = getString(evaluator, row, 6);
                // 具体经营项目
                String detail = getString(evaluator, row, 7);
                // 是否有违法
                String illegal = getString(evaluator, row, 8);
                // 是否有行政处罚
                String penalty = getString(evaluator, row, 9);
                // 是否经常异常
                String exception = getString(evaluator, row, 10);
                // 链接
                String link = getString(evaluator, row, 11);
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
