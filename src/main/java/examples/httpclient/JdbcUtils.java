package examples.httpclient;

import ch17database.Record;
import examples.pool.dbutils.JdbcUtil;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-6-12
 * Time: 上午11:24
 * JdbcUtils
 */
public class JdbcUtils {
    private static final Logger _log = LoggerFactory.getLogger(JdbcUtil.class);
    /**
     * 测试JdbcUtils的各个方法
     *
     */
    public static void main(String[] args) throws Exception {
//        startIdMap(new HashMap<>());
//        /******************测试插入操作*****************/
//        String insertSql = "insert into record(description,content,createdTime,modifyTime) values (?,?,?,?)";
//        for (int i = 0; i < 100; i++) {
//            List<String> insertParam = new ArrayList<String>();
//            insertParam.add("this is record " + i);
//            insertParam.add("record " + i + "content");
//            insertParam.add("2012-07-04");
//            insertParam.add("2012-07-04");
//            updateDataByJdbc(connection, insertSql, insertParam);
//        }
//
//        /******************测试更新操作*****************/
//        String updateSql = "update record set description=? where id=?";
//        List<String> updateParam = new ArrayList<String>();
//        updateParam.add("update update update");
//        updateParam.add("1");
//        updateDataByJdbc(connection, updateSql, updateParam);
//
//        /******************测试删除操作*****************/
//        String deleteSql = "delete from record where id=?";
//        List<String> deleteParam = new ArrayList<String>();
//        deleteParam.add("2");
//        updateDataByJdbc(connection, deleteSql, deleteParam);
//
//        /******************测试数目查询操作*****************/
//        String countSql = "select count(*) from record where content like ? ; ";
//        List<String> countParam = new ArrayList<String>();
//        countParam.add("%cont%");
//        System.out.println("查询总数目为" + queryCountByJdbc(connection, countSql, countParam));
//
//        /******************测试普通查询操作*****************/
//        String querySql = "select * from record where content like ?";
//        List<String> queryParam = new ArrayList<String>();
//        queryParam.add("%cont%");
//        List<Record> commonList = queryDataByJdbc(connection, querySql, queryParam);
//        System.out.println("普通查询结果：");
//        for (Record r : commonList) {
//            System.out.println(r);
//        }
//
//        /******************测试分页查询操作*****************/
//        String pageQuerySql = "select * from record where content like ?";
//        List<String> pageQueryParam = new ArrayList<String>();
//        pageQueryParam.add("%cont%");
//        List<Record> pageList = queryPageDataByJdbc(connection, pageQuerySql, pageQueryParam, 20, 20);
//        System.out.println("分页查询结果：");
//        for (Record r : pageList) {
//            System.out.println(r);
//        }
    }

    /**
     * 初始化IDMAP
     * @param idmap
     */
    public static synchronized void startIdMap(Map<String, String> idmap, Set<String> nameSet) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlText = "SELECT name, link FROM t_idmap;";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlText);
            rs = ps.executeQuery();
            while (rs.next()) {
                String n = rs.getString("name");
                if (nameSet.contains(n)) {
                    idmap.put(n, rs.getString("link"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取公司列表
     * @param idmap
     */
    public static List<Company> selectCompanys() {
        List<Company> companies = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlText = "SELECT " +
                "result_type" +
                ",company_name" +
                ",taxno" +
                ",law_person" +
                ",reg_date" +
                ",location" +
                ",business" +
                ",stockholder" +
                ",detail" +
                ",illegal" +
                ",penalty" +
                ",exception" +
                ",status" +
                ",link " +
                "FROM t_scompany";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlText);
            rs = ps.executeQuery();
            while (rs.next()) {
                Company c = new Company();
                c.setResultType(rs.getInt(1));
                c.setCompanyName(rs.getString(2));
                c.setTaxno(rs.getString(3));
                c.setLawPerson(rs.getString(4));
                java.sql.Date rd = rs.getDate(5);
                if (rd != null) {
                    c.setRegDate(new java.util.Date(rs.getDate(5).getTime()));
                }
                c.setLocation(rs.getString(6));
                c.setBusiness(rs.getString(7));
                c.setStockholder(rs.getString(8));
                c.setDetail(rs.getString(9));
                c.setIllegal(rs.getString(10));
                c.setPenalty(rs.getString(11));
                c.setException(rs.getString(12));
                c.setStatus(rs.getString(13));
                c.setLink(rs.getString(14));
                companies.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return companies;
    }

    /**
     * 根据配置文件获取企业名列表
     *
     * @param fileName 企业名列表文件名
     */
    public static List<String> getNames(String fileName) {
        List<String> result = new ArrayList<>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            String compName;
            while ((compName = in.readLine()) != null) {
                if (StringUtil.isNotBlank(compName)) {
                    _log.info("---企业名：" + compName);
                    compName = compName.replaceAll("（", "(").replaceAll("）", ")");
                    result.add(compName.trim());
                }
            }
        } catch (Exception e) {
            _log.error("读取企业名文件出错。", e);
        } finally {
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static String selectTaxcode(String compName) {
        String result = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlText = "SELECT cust_tax_code " +
                "FROM t_crm_company WHERE cust_name=? OR cust_name=? LIMIT 1;";
        String sqlText2 = "SELECT SellerTaxCode FROM t_invoice " +
                "WHERE Seller=? LIMIT 1";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlText);
            ps.setString(1, compName);
            ps.setString(2, compName);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getString(1);
            } else {
                ps = conn.prepareStatement(sqlText2);
                ps.setString(1, compName);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getString(1);
                }
            }
        } catch (Exception e) {
            _log.error("查询税号出错。", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 根据企业名配置文件查找存在发票记录的企业名
     * @param filename
     * @return
     */
    public static List<String> selectInvoiceExsits(String filename) {
        List<String> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlText = "SELECT COUNT(*) FROM t_invoice WHERE Seller=?;";
        List<String> compNames = getNames(filename);
        try {
            conn = getConnection();
            for (String n : compNames) {
                ps = conn.prepareStatement(sqlText);
                ps.setString(1, n);
                rs = ps.executeQuery();
                if (rs.next()) {
                    if (rs.getInt(1) > 0) {
                        result.add(n);
                    }
                }
            }
        } catch (Exception e) {
            _log.error("查询税号出错。", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }



    /**
     * 获取发票列表
     * @param idmap
     */
    public static List<Invoice> selectInvoices(String taxcode) {
        List<Invoice> invoices = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlText = "SELECT " +
                "A.InvNo" +
                ",A.InvDate" +
                ",A.InvKind" +
                ",A.Seller" +
                ",A.SellerTaxCode" +
                ",A.SellerAccounts" +
                ",A.SellerAddress" +
                ",A.Amount" +
                ",A.Buyer" +
                ",A.BuyTaxCode" +
                ",A.BuyerAccounts" +
                ",A.BuyerAddress" +
                ",A.Bszt" +
                ",A.KPR " +
                ",A.ListFlag " +
                ",A.MachineNum " +
                ",A.Memo " +
                ",A.Tax " +
                ",A.TaxRate " +
                ",A.TypeCode " +
                ",A.WareName " +
                ",A.Zfbz " +
                "FROM `t_invoice` A WHERE A.SellerTaxCode=?;";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlText);
            ps.setString(1, taxcode);
            rs = ps.executeQuery();
            while (rs.next()) {
                Invoice c = new Invoice();
                c.setInvNo(rs.getString(1));
                c.setInvDate(DateUtil.sqlToDate(rs.getDate(2)));
                c.setInvKind(rs.getString(3));
                c.setSeller(rs.getString(4));
                c.setSellerTaxCode(rs.getString(5));
                c.setSellerAccounts(rs.getString(6));
                c.setSellerAddress(rs.getString(7));
                c.setAmount(rs.getDouble(8));
                c.setBuyer(rs.getString(9));
                c.setBuyTaxCode(rs.getString(10));
                c.setBuyerAccounts(rs.getString(11));
                c.setBuyerAddress(rs.getString(12));
                c.setBszt(rs.getString(13));
                c.setKPR(rs.getString(14));
                c.setListFlag(rs.getString(15));
                c.setMachineNum(rs.getInt(16));
                c.setMemo(rs.getString(17));
                c.setTax(rs.getFloat(18));
                c.setTaxRate(rs.getFloat(19));
                c.setTypeCode(rs.getString(20));
                c.setWareName(rs.getString(21));
                c.setZfbz(rs.getString(22));
                invoices.add(c);
            }
        } catch (Exception e) {
            _log.error("查询Invoice出错。", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return invoices;
    }

    /**
     * 获取发票列表
     * @param idmap
     */
    public static List<InvoiceDetail> selectInvoiceDetails(String taxcode) {
        List<InvoiceDetail> invoiceDetails = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlText = "SELECT " +
                "B.InvNo AS InvNo" +
                ",A.Wares_Amount AS Wares_Amount" +
                ",A.Wares_LineType AS Wares_LineType" +
                ",A.Wares_Number AS Wares_Number" +
                ",A.Wares_Price AS Wares_Price" +
                ",A.Wares_Standard AS Wares_Standard" +
                ",A.Wares_Tax AS Wares_Tax" +
                ",A.Wares_TaxItem AS Wares_TaxItem" +
                ",A.Wares_TaxRate AS Wares_TaxRate" +
                ",A.Wares_TaxTag AS Wares_TaxTag" +
                ",A.Wares_Unit AS Wares_Unit" +
                ",A.Wares_WareName AS Wares_WareName " +
                " FROM t_invoice_detail A LEFT OUTER JOIN t_invoice B ON A.invoice_id=B.id" +
                " WHERE B.SellerTaxCode = ?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sqlText);
            ps.setString(1, taxcode);
            rs = ps.executeQuery();
            while (rs.next()) {
                InvoiceDetail c = new InvoiceDetail();
                c.setInvNo(rs.getString(1));
                c.setWares_Amount(rs.getDouble(2));
                c.setWares_LineType(rs.getString(3));
                c.setWares_Number(rs.getInt(4));
                c.setWares_Price(rs.getFloat(5));
                c.setWares_Standard(rs.getString(6));
                c.setWares_Tax(rs.getDouble(7));
                c.setWares_TaxItem(rs.getString(8));
                c.setWares_TaxRate(rs.getFloat(9));
                c.setWares_TaxTag(rs.getString(10));
                c.setWares_Unit(rs.getString(11));
                c.setWares_WareName(rs.getString(12));

                invoiceDetails.add(c);
            }
            Collections.sort(invoiceDetails, new Comparator<InvoiceDetail>() {
                @Override
                public int compare(InvoiceDetail o1, InvoiceDetail o2) {
                    return o1.getInvNo().compareTo(o2.getInvNo());
                }
            });
        } catch (Exception e) {
            _log.error("查询Invoice出错。", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return invoiceDetails;
    }

    private static final Lock idmLock = new ReentrantLock();
    /**
     * 更新IDMAP
     * @param map
     */
    public static void endIdMap(LinkedBlockingQueue<String[]> idMapQueue) {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        String selectSQL = "SELECT COUNT(*) FROM t_idmap WHERE name=?";
        String insertSQL = "INSERT INTO t_idmap(name, link) VALUES(?,?)";
        String updateSQL = "UPDATE t_idmap SET link=? WHERE name=?";
        try {
            conn = getConnection();
            while(true) {
                String[] idm = null;
                if (idmLock.tryLock()) {
                    try {
                        idm = idMapQueue.poll();;
                    } finally {
                        idmLock.unlock();
                    }
                }
                if (idm == null) break;
                String k = idm[0];
                String v = idm[1];
                ps1 = conn.prepareStatement(selectSQL);
                ps1.setString(1, k);
                int count = 0;
                ResultSet rs = ps1.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
                if (count == 0) {
                    ps2 = conn.prepareStatement(insertSQL);
                    ps2.setString(1, k);
                    ps2.setString(2, v);
                    ps2.executeUpdate();
                } else {
                    ps3 = conn.prepareStatement(updateSQL);
                    ps3.setString(1, v);
                    ps3.setString(2, k);
                    ps3.executeUpdate();
                }
                ps1.close();
                if (ps2 != null) {
                    ps2.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
                //_log.info("iter.remove()....");
            }
            _log.info("----endIdMap进程执行完成----");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps1 != null) {
                    ps1.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化公司表
     * @param map
     */
    public static void startCompany(LinkedBlockingQueue<String> redoQueue) {
        Connection conn = null;
        String clearSQL = "DELETE FROM t_scompany";
        String insertSQL = "INSERT INTO t_scompany(company_name) VALUES(?)";
        try {
            conn = getConnection();
            PreparedStatement ps1 = conn.prepareStatement(clearSQL);
            ps1.executeUpdate();
            ps1.close();
            while (true) {
                String k = redoQueue.poll();
                if (k == null) break;
                PreparedStatement ps2 = conn.prepareStatement(insertSQL);
                ps2.setString(1, k);
                ps2.executeUpdate();
                ps2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static final Lock clock = new ReentrantLock();
    /**
     * 更新公司表
     * @param map
     */
    public static void endCompany(LinkedBlockingQueue<Company> compQueue) {
        Connection conn = null;
        String selectSQL = "SELECT id FROM t_scompany WHERE company_name=? LIMIT 1";
        String insertSQL = "INSERT INTO t_scompany(" +
                "result_type" +
                ",taxno" +
                ",law_person" +
                ",reg_date" +
                ",location" +
                ",business" +
                ",stockholder" +
                ",detail" +
                ",illegal" +
                ",penalty" +
                ",exception" +
                ",status" +
                ",link" +
                ",company_name)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String updateSQL = "UPDATE t_scompany SET " +
                "result_type=?" +
                ",taxno=?" +
                ",law_person=?" +
                ",reg_date=?" +
                ",location=?" +
                ",business=?" +
                ",stockholder=?" +
                ",detail=?" +
                ",illegal=?" +
                ",penalty=?" +
                ",exception=?" +
                ",status=?" +
                ",link=? " +
                "WHERE company_name=?";
        try {
            conn = getConnection();
            //队列方式遍历，元素逐个被移除
            while (true) {
                Company c =null;
                if (clock.tryLock()) {
                    try {
                        c = compQueue.poll();
                    } finally {
                        clock.unlock();
                    }
                }
                if (c == null) break;
                PreparedStatement ps1 = conn.prepareStatement(selectSQL);
                ps1.setString(1, c.getCompanyName());
                ResultSet rs1 = ps1.executeQuery();
                long cid = -1L;
                if (rs1.next()) {
                    cid = rs1.getLong(1);
                }
                rs1.close();
                if (cid == -1L) {
                    PreparedStatement ps11 = conn.prepareStatement(insertSQL);
                    ps11.setInt(1, c.getResultType());
                    ps11.setString(2, c.getTaxno());
                    ps11.setString(3, c.getLawPerson());
                    if (c.getRegDate() != null) {
                        ps11.setDate(4, new java.sql.Date(c.getRegDate().getTime()));
                    } else {
                        ps11.setDate(4, null);
                    }
                    ps11.setString(5, c.getLocation());
                    ps11.setString(6, c.getBusiness());
                    ps11.setString(7, c.getStockholder());
                    ps11.setString(8, c.getDetail());
                    ps11.setString(9, c.getIllegal());
                    ps11.setString(10, c.getPenalty());
                    ps11.setString(11, c.getException());
                    ps11.setString(12, c.getStatus());
                    ps11.setString(13, c.getLink());
                    ps11.setString(14, c.getCompanyName());

                    ps11.executeUpdate();
                    ps11.close();
                } else {
                    PreparedStatement ps2 = conn.prepareStatement(updateSQL);
                    ps2.setInt(1, c.getResultType());
                    ps2.setString(2, c.getTaxno());
                    ps2.setString(3, c.getLawPerson());
                    if (c.getRegDate() != null) {
                        ps2.setDate(4, new java.sql.Date(c.getRegDate().getTime()));
                    } else {
                        ps2.setDate(4, null);
                    }
                    ps2.setString(5, c.getLocation());
                    ps2.setString(6, c.getBusiness());
                    ps2.setString(7, c.getStockholder());
                    ps2.setString(8, c.getDetail());
                    ps2.setString(9, c.getIllegal());
                    ps2.setString(10, c.getPenalty());
                    ps2.setString(11, c.getException());
                    ps2.setString(12, c.getStatus());
                    ps2.setString(13, c.getLink());
                    ps2.setString(14, c.getCompanyName());

                    ps2.executeUpdate();
                    ps2.close();
                }

            }
            _log.info("----endCompany进程执行完成----");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新重做表
     * @param map
     */
    public static void endRedo(LinkedBlockingQueue<String> redoBug) {
        Connection conn = null;
        PreparedStatement ps1;
        PreparedStatement ps2;
        String deleteSQL = "DELETE FROM t_redo";
        String insertSQL = "INSERT INTO t_redo(name) VALUES(?)";
        try {
            conn = getConnection();
            ps1 = conn.prepareStatement(deleteSQL);
            ps1.executeUpdate();
            ps1.close();
            while (true) {
                String n = redoBug.poll();
                if (n == null) break;
                ps2 = conn.prepareStatement(insertSQL);
                ps2.setString(1, n);
                ps2.executeUpdate();
                ps2.close();
            }
            _log.info("----更新重做表进程执行完成----");
        } catch (Exception e) {
            _log.error("更新重做表SQL错误", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     * @throws Exception
     */
    private static Connection getConnection() throws Exception {
        Connection conn = null;
        try {
//            Properties props = new Properties();
            PropertiesConfiguration props = new PropertiesConfiguration("jdbc.properties");
//            FileInputStream jdbcConfig = new FileInputStream("src/main/resources/jdbc.properties");
//            props.load(jdbcConfig);
//            jdbcConfig.close();
            String driver = props.getString("jdbc.driver");
            if (driver != null) {
                Class.forName(driver);
            }
            String url = props.getString("jdbc.url");
            String username = props.getString("jdbc.username");
            String password = props.getString("jdbc.password");
            conn = DriverManager.getConnection(url, username, password);
            _log.info("Database connection established");
        } catch (Exception e) {
            _log.error("Cannot connect to database server");
            if (conn != null) {
                conn.close();
                _log.info("Database connection terminated");
                return null;
            }
        }
        return conn;
    }

    /**
     * 数目查询操作
     *
     * @param conn    数据库连接
     * @param sqlText sql文
     * @param params  参数列表
     * @return 查询数目
     * @throws Exception
     */
    private static int queryCountByJdbc(Connection conn, String sqlText, List<String> params) throws Exception {
        int result = 0;
        PreparedStatement ps = conn.prepareStatement(sqlText);
        int index = 1;
        for (String p : params) {
            ps.setString(index++, p);
        }
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            result = rs.getInt(1);
        }
        rs.close();
        ps.close();
        return result;
    }

    /**
     * 普通查询操作
     *
     * @param conn    数据库连接
     * @param sqlText sql文
     * @param params  参数列表
     * @return 检索结果Record记录
     * @throws Exception
     */
    private static List<Record> queryDataByJdbc(
            Connection conn, String sqlText, List<String> params) throws Exception {
        PreparedStatement ps = conn.prepareStatement(sqlText);
        int index = 1;
        for (String p : params) {
            ps.setString(index++, p);
        }
        ResultSet rs = ps.executeQuery();
        // do something....
        List<Record> records = new ArrayList<Record>();
        while (rs.next()) {
            Record record = new Record();
            record.setId(rs.getLong("id"));
            record.setDescription(rs.getString("description"));
            record.setContent(rs.getString("contENt"));
            record.setCreatedTime(rs.getDate("createdtime"));
            record.setModifyTime(rs.getTimestamp("modifytime"));
            records.add(record);
        }
        rs.close();
        ps.close();
        return records;
    }

    /**
     * 分页查询操作
     *
     * @param conn    数据库连接
     * @param sqlText sql文
     * @param params  参数列表
     * @param offset  偏移量
     * @param max     页面最大值
     * @return 检索结果Record记录
     * @throws Exception
     */
    private static List<Record> queryPageDataByJdbc(Connection conn, String sqlText,
                                                    List<String> params, long offset, int max) throws Exception {
        PreparedStatement ps = conn.prepareStatement(sqlText + " LIMIT " + offset + ", " + max);
        int index = 1;
        for (String p : params) {
            ps.setString(index++, p);
        }
        ResultSet rs = ps.executeQuery();
        // do something....
        List<Record> records = new ArrayList<Record>();
        while (rs.next()) {
            Record record = new Record();
            record.setId(rs.getLong("id"));
            record.setDescription(rs.getString("description"));
            record.setContent(rs.getString("contENt"));
            record.setCreatedTime(rs.getDate("createdtime"));
            record.setModifyTime(rs.getTimestamp("modifytime"));
            records.add(record);
        }
        rs.close();
        ps.close();
        return records;
    }

    /**
     * 增删改操作
     *
     * @param conn    数据库连接
     * @param sqlText sql文
     * @param params  参数列表
     * @return 增删改成功数目
     */
    private static int updateDataByJdbc(Connection conn, String sqlText, List<String> params) throws Exception {
        PreparedStatement ps = conn.prepareStatement(sqlText);
        int index = 1;
        for (String p : params) {
            ps.setString(index++, p);
        }
        int count = ps.executeUpdate();
        ps.close();
        return count;
    }
}
