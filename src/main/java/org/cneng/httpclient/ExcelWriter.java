package org.cneng.httpclient;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public ExcelWriter() {
        wb = new SXSSFWorkbook(new XSSFWorkbook(), 500);
    }

    public void generate(List<Company> companyList, String filename) {
        try {
            // 在webbook中添加一个sheet---Sheet1
            Sheet sheet1 = wb.createSheet("Sheet1");
            sheet1.setColumnWidth(0, 40 * 256);
            sheet1.setColumnWidth(1, 30 * 256);
            sheet1.setColumnWidth(2, 20 * 256);
            sheet1.setColumnWidth(3, 20 * 256);
            sheet1.setColumnWidth(4, 40 * 256);
            sheet1.setColumnWidth(5, 60 * 256);
            sheet1.setColumnWidth(6, 20 * 256);
            sheet1.setColumnWidth(7, 16 * 256);
            sheet1.setColumnWidth(8, 16 * 256);
            sheet1.setColumnWidth(9, 16 * 256);
            sheet1.setColumnWidth(10, 16 * 256);
            sheet1.setColumnWidth(11, 100 * 256);
            // 在sheet中添加表头第0行
            Row row = sheet1.createRow(0);
            row.setHeight((short) 400);//目的是想把行高设置成25px
            // 创建单元格，并设置值表头 设置表头居中
            CellStyle style = wb.createCellStyle();
            style.setBorderBottom(CellStyle.BORDER_THIN);
            style.setBorderLeft(CellStyle.BORDER_THIN);
            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setBorderTop(CellStyle.BORDER_THIN);
            style.setAlignment(CellStyle.ALIGN_CENTER); // 创建一个居中格式
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 12);//字号
            font.setColor(Font.COLOR_NORMAL);
            style.setFont(font);
            style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex()); //背景颜色
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);

            Cell cell = row.createCell(0);
            cell.setCellValue("企业名称");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue("注册号");
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue("法定代表人");
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue("成立日期");
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue("住所");
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue("经营范围");
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue("股东/发起人");
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue("具体经营项目");
            cell.setCellStyle(style);

            cell = row.createCell(8);
            cell.setCellValue("是否有违法");
            cell.setCellStyle(style);

            cell = row.createCell(9);
            cell.setCellValue("是否有行政处罚");
            cell.setCellStyle(style);

            cell = row.createCell(10);
            cell.setCellValue("是否经营异常");
            cell.setCellStyle(style);

            cell = row.createCell(11);
            cell.setCellValue("链接");
            cell.setCellStyle(style);

            // 设置内容样式
            CellStyle style2 = wb.createCellStyle();
//        style2.setBorderBottom(CellStyle.BORDER_MEDIUM);
//        style2.setBorderLeft(CellStyle.BORDER_MEDIUM);
//        style2.setBorderRight(CellStyle.BORDER_MEDIUM);
//        style2.setBorderTop(CellStyle.BORDER_MEDIUM);
            CellStyle styleRed = wb.createCellStyle();
            Font fontRed = wb.createFont();
            fontRed.setFontHeightInPoints((short) 12);//字号
            fontRed.setColor(Font.COLOR_RED);
            styleRed.setFont(fontRed);
            // 写入实体数据
            for (int i = 0; i < companyList.size(); i++) {
                row = sheet1.createRow(i + 1);
                Company company = companyList.get(i);

                if ("存续".equals(company.getStatus())) {
                    cell = row.createCell(0);
                    cell.setCellValue(company.getCompanyName());
                    cell.setCellStyle(style2);

                    cell = row.createCell(1);
                    cell.setCellValue(company.getTaxno());
                    cell.setCellStyle(style2);

                    cell = row.createCell(2);
                    cell.setCellValue(company.getLawPerson());
                    cell.setCellStyle(style2);

                    cell = row.createCell(3);
                    cell.setCellValue(DateUtil.toStr(company.getRegDate()));
                    cell.setCellStyle(style2);

                    cell = row.createCell(4);
                    cell.setCellValue(company.getLocation());
                    cell.setCellStyle(style2);

                    cell = row.createCell(5);
                    cell.setCellValue(company.getBusiness());
                    cell.setCellStyle(style2);

                    cell = row.createCell(6);
                    cell.setCellValue(company.getStockholder());
                    cell.setCellStyle(style2);

                    cell = row.createCell(7);
                    cell.setCellValue(company.getDetail());
                    cell.setCellStyle(style2);

                    cell = row.createCell(8);
                    cell.setCellValue(company.getIllegal());
                    cell.setCellStyle(style2);

                    cell = row.createCell(9);
                    cell.setCellValue(company.getPenalty());
                    cell.setCellStyle(style2);

                    cell = row.createCell(10);
                    cell.setCellValue(company.getException());
                    cell.setCellStyle(style2);

                    cell = row.createCell(11);
                    cell.setCellValue(company.getLink());
                    cell.setCellStyle(style2);

                } else {
                    cell = row.createCell(0);
                    cell.setCellValue(company.getCompanyName());
                    cell.setCellStyle(styleRed);
                }
            }
            FileOutputStream fout = new FileOutputStream(filename);
            wb.write(fout);
            fout.close();
        } catch (Exception e) {
            _log.error("excel文件导出出错。", e);
        } finally {
            try {
                wb.close();
            } catch (IOException e) {
                _log.error("wb.close()出错。", e);
            }
        }
    }

    public static void main(String[] args) {
        List<Company> companies = JdbcUtils.selectCompanys();
        new ExcelWriter().generate(companies, "D:/work/企业名单数据.xlsx");
    }
}
