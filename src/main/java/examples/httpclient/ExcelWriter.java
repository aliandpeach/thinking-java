package examples.httpclient;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.apache.poi.ss.usermodel.BorderStyle.THIN;

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
            style.setBorderBottom(THIN);
            style.setBorderLeft(THIN);
            style.setBorderRight(THIN);
            style.setBorderTop(THIN);
            style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 12);//字号
            font.setColor(Font.COLOR_NORMAL);
            style.setFont(font);
            style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex()); //背景颜色
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

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

    /**
     * 导出发票
     * @param invoiceList
     * @param filename
     */
    public void generateInvoice(List<Invoice> invoiceList, String filename) {
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
            sheet1.setColumnWidth(11, 20 * 256);
            sheet1.setColumnWidth(12, 20 * 256);
            sheet1.setColumnWidth(13, 20 * 256);
            sheet1.setColumnWidth(14, 20 * 256);
            sheet1.setColumnWidth(15, 20 * 256);
            sheet1.setColumnWidth(16, 20 * 256);
            sheet1.setColumnWidth(17, 20 * 256);
            sheet1.setColumnWidth(18, 20 * 256);
            sheet1.setColumnWidth(19, 20 * 256);
            sheet1.setColumnWidth(20, 20 * 256);
            sheet1.setColumnWidth(21, 20 * 256);
            // 在sheet中添加表头第0行
            Row row = sheet1.createRow(0);
            row.setHeight((short) 400);//目的是想把行高设置成25px
            // 创建单元格，并设置值表头 设置表头居中
            CellStyle style = wb.createCellStyle();
            style.setBorderBottom(THIN);
            style.setBorderLeft(THIN);
            style.setBorderRight(THIN);
            style.setBorderTop(THIN);
            style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 12);//字号
            font.setColor(Font.COLOR_NORMAL);
            style.setFont(font);
            style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex()); //背景颜色
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Cell cell = row.createCell(0);
            cell.setCellValue("发票号码");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue("开票日期");
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue("发票种类");
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue("销方名称");
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue("销方税号");
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue("销方银行账号");
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue("销方地址电话");
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue("金额");
            cell.setCellStyle(style);

            cell = row.createCell(8);
            cell.setCellValue("购方名称");
            cell.setCellStyle(style);

            cell = row.createCell(9);
            cell.setCellValue("购方税号");
            cell.setCellStyle(style);

            cell = row.createCell(10);
            cell.setCellValue("购方银行账号");
            cell.setCellStyle(style);

            cell = row.createCell(11);
            cell.setCellValue("购方地址电话");
            cell.setCellStyle(style);

            cell = row.createCell(12);
            cell.setCellValue("报税状态");
            cell.setCellStyle(style);

            cell = row.createCell(13);
            cell.setCellValue("开票人");
            cell.setCellStyle(style);

            cell = row.createCell(14);
            cell.setCellValue("清单标志");
            cell.setCellStyle(style);

            cell = row.createCell(15);
            cell.setCellValue("开票机号");
            cell.setCellStyle(style);

            cell = row.createCell(16);
            cell.setCellValue("备注");
            cell.setCellStyle(style);

            cell = row.createCell(17);
            cell.setCellValue("税额");
            cell.setCellStyle(style);

            cell = row.createCell(18);
            cell.setCellValue("税率");
            cell.setCellStyle(style);

            cell = row.createCell(19);
            cell.setCellValue("发票代码");
            cell.setCellStyle(style);

            cell = row.createCell(20);
            cell.setCellValue("主要商品名称");
            cell.setCellStyle(style);

            cell = row.createCell(21);
            cell.setCellValue("作废标志");
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
            for (int i = 0; i < invoiceList.size(); i++) {
                row = sheet1.createRow(i + 1);
                Invoice invoice = invoiceList.get(i);

                cell = row.createCell(0);
                cell.setCellValue(invoice.getInvNo());
                cell.setCellStyle(style2);

                cell = row.createCell(1);
                cell.setCellValue(DateUtil.toStr(invoice.getInvDate()));
                cell.setCellStyle(style2);

                cell = row.createCell(2);
                cell.setCellValue(invoice.getInvKind());
                cell.setCellStyle(style2);

                cell = row.createCell(3);
                cell.setCellValue(invoice.getSeller());
                cell.setCellStyle(style2);

                cell = row.createCell(4);
                cell.setCellValue(invoice.getSellerTaxCode());
                cell.setCellStyle(style2);

                cell = row.createCell(5);
                cell.setCellValue(invoice.getSellerAccounts());
                cell.setCellStyle(style2);

                cell = row.createCell(6);
                cell.setCellValue(invoice.getSellerAddress());
                cell.setCellStyle(style2);

                cell = row.createCell(7);
                cell.setCellValue(invoice.getAmount());
                cell.setCellStyle(style2);

                cell = row.createCell(8);
                cell.setCellValue(invoice.getBuyer());
                cell.setCellStyle(style2);

                cell = row.createCell(9);
                cell.setCellValue(invoice.getBuyTaxCode());
                cell.setCellStyle(style2);

                cell = row.createCell(10);
                cell.setCellValue(invoice.getBuyerAccounts());
                cell.setCellStyle(style2);

                cell = row.createCell(11);
                cell.setCellValue(invoice.getBuyerAddress());
                cell.setCellStyle(style2);

                cell = row.createCell(12);
                cell.setCellValue(invoice.getBszt());
                cell.setCellStyle(style2);

                cell = row.createCell(13);
                cell.setCellValue(invoice.getKPR());
                cell.setCellStyle(style2);

                cell = row.createCell(14);
                cell.setCellValue(invoice.getListFlag());
                cell.setCellStyle(style2);

                cell = row.createCell(15);
                cell.setCellValue(invoice.getMachineNum());
                cell.setCellStyle(style2);

                cell = row.createCell(16);
                cell.setCellValue(invoice.getMemo());
                cell.setCellStyle(style2);

                cell = row.createCell(17);
                cell.setCellValue(invoice.getTax());
                cell.setCellStyle(style2);

                cell = row.createCell(18);
                cell.setCellValue(invoice.getTaxRate());
                cell.setCellStyle(style2);

                cell = row.createCell(19);
                cell.setCellValue(invoice.getTypeCode());
                cell.setCellStyle(style2);

                cell = row.createCell(20);
                cell.setCellValue(invoice.getWareName());
                cell.setCellStyle(style2);

                cell = row.createCell(21);
                cell.setCellValue(invoice.getZfbz());
                cell.setCellStyle(style2);
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

    /**
     * 导出发票明细
     * @param invoiceDetailList
     * @param filename
     */
    public void generateInvoiceDetails(List<InvoiceDetail> invoiceDetailList, String filename) {
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
            sheet1.setColumnWidth(11, 20 * 256);
            // 在sheet中添加表头第0行
            Row row = sheet1.createRow(0);
            row.setHeight((short) 400);//目的是想把行高设置成25px
            // 创建单元格，并设置值表头 设置表头居中
            CellStyle style = wb.createCellStyle();
            style.setBorderBottom(THIN);
            style.setBorderLeft(THIN);
            style.setBorderRight(THIN);
            style.setBorderTop(THIN);
            style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 12);//字号
            font.setColor(Font.COLOR_NORMAL);
            style.setFont(font);
            style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex()); //背景颜色
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Cell cell = row.createCell(0);
            cell.setCellValue("发票号码");
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue("商品金额");
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue("商品行性质");
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue("商品数量");
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue("商品价格");
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue("商品规格型号");
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue("商品税额");
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue("商品税目");
            cell.setCellStyle(style);

            cell = row.createCell(8);
            cell.setCellValue("商品税率");
            cell.setCellStyle(style);

            cell = row.createCell(9);
            cell.setCellValue("含税价标志");
            cell.setCellStyle(style);

            cell = row.createCell(10);
            cell.setCellValue("商品计量单位");
            cell.setCellStyle(style);

            cell = row.createCell(11);
            cell.setCellValue("商品名称");
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
            for (int i = 0; i < invoiceDetailList.size(); i++) {
                row = sheet1.createRow(i + 1);
                InvoiceDetail invoiceD = invoiceDetailList.get(i);

                cell = row.createCell(0);
                cell.setCellValue(invoiceD.getInvNo());
                cell.setCellStyle(style2);

                cell = row.createCell(1);
                cell.setCellValue(invoiceD.getWares_Amount());
                cell.setCellStyle(style2);

                cell = row.createCell(2);
                cell.setCellValue(invoiceD.getWares_LineType());
                cell.setCellStyle(style2);

                cell = row.createCell(3);
                cell.setCellValue(invoiceD.getWares_Number());
                cell.setCellStyle(style2);

                cell = row.createCell(4);
                cell.setCellValue(invoiceD.getWares_Price());
                cell.setCellStyle(style2);

                cell = row.createCell(5);
                cell.setCellValue(invoiceD.getWares_Standard());
                cell.setCellStyle(style2);

                cell = row.createCell(6);
                cell.setCellValue(invoiceD.getWares_Tax());
                cell.setCellStyle(style2);

                cell = row.createCell(7);
                cell.setCellValue(invoiceD.getWares_TaxItem());
                cell.setCellStyle(style2);

                cell = row.createCell(8);
                cell.setCellValue(invoiceD.getWares_TaxRate());
                cell.setCellStyle(style2);

                cell = row.createCell(9);
                cell.setCellValue(invoiceD.getWares_TaxTag());
                cell.setCellStyle(style2);

                cell = row.createCell(10);
                cell.setCellValue(invoiceD.getWares_Unit());
                cell.setCellStyle(style2);

                cell = row.createCell(11);
                cell.setCellValue(invoiceD.getWares_WareName());
                cell.setCellStyle(style2);

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
//        List<Company> companies = JdbcUtils.selectCompanys();
        String f = ConfigUtil.get("names_file");
        String outDir = ConfigUtil.get("excel_out_dir");
        List<String> names = JdbcUtils.getNames(f);
        for (String name : names) {
            String taxcode = JdbcUtils.selectTaxcode(name);
            List<Invoice> invoices = JdbcUtils.selectInvoices(taxcode);
            List<InvoiceDetail> invoiceDetails = JdbcUtils.selectInvoiceDetails(taxcode);
            if (invoices != null && invoices.size() > 0) {
                new ExcelWriter().generateInvoice(invoices, String.format("%s/invoices_%s.xlsx", outDir, name));
                new ExcelWriter().generateInvoiceDetails(invoiceDetails, String.format("%s/invoiceDetails_%s.xlsx", outDir, name));
            }
        }
//        System.out.println(JdbcUtils.selectInvoiceExsits(f).size());
    }
}
