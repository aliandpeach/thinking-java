package ch09samples.jdbc;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-4
 * Time: 上午11:24
 * JdbcUtils
 */
public class JdbcUtils {
    /**
     * 测试JdbcUtils的各个方法
     *
     * @param args
     * @throws Exception 表结构：
     *                   CREATE TABLE `record` (
     *                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
     *                   `description` varchar(255) DEFAULT NULL,
     *                   `content` text,
     *                   `createdTime` datetime DEFAULT NULL,
     *                   `modifyTime` datetime DEFAULT NULL,
     *                   PRIMARY KEY (`id`)
     *                   ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     */
    public static void main(String[] args) throws Exception {
        Connection connection = getConnection();

        /******************测试插入操作*****************/
        String insertSql = "insert into record(description,content,createdTime,modifyTime) values (?,?,?,?)";
        for (int i = 0; i < 100; i++) {
            List<String> insertParam = new ArrayList<String>();
            insertParam.add("this is record " + i);
            insertParam.add("record " + i + "content");
            insertParam.add("2012-07-04");
            insertParam.add("2012-07-04");
            updateDataByJdbc(connection, insertSql, insertParam);
        }

        /******************测试更新操作*****************/
        String updateSql = "update record set description=? where id=?";
        List<String> updateParam = new ArrayList<String>();
        updateParam.add("update update update");
        updateParam.add("1");
        updateDataByJdbc(connection, updateSql, updateParam);

        /******************测试删除操作*****************/
        String deleteSql = "delete from record where id=?";
        List<String> deleteParam = new ArrayList<String>();
        deleteParam.add("2");
        updateDataByJdbc(connection, deleteSql, deleteParam);

        /******************测试数目查询操作*****************/
        String countSql = "select count(*) from record where content like ? ; ";
        List<String> countParam = new ArrayList<String>();
        countParam.add("%cont%");
        System.out.println("查询总数目为" + queryCountByJdbc(connection, countSql, countParam));

        /******************测试普通查询操作*****************/
        String querySql = "select * from record where content like ?";
        List<String> queryParam = new ArrayList<String>();
        queryParam.add("%cont%");
        List<Record> commonList = queryDataByJdbc(connection, querySql, queryParam);
        System.out.println("普通查询结果：");
        for (Record r : commonList) {
            System.out.println(r);
        }

        /******************测试分页查询操作*****************/
        String pageQuerySql = "select * from record where content like ?";
        List<String> pageQueryParam = new ArrayList<String>();
        pageQueryParam.add("%cont%");
        List<Record> pageList = queryPageDataByJdbc(connection, pageQuerySql, pageQueryParam, 20, 20);
        System.out.println("分页查询结果：");
        for (Record r : pageList) {
            System.out.println(r);
        }
        connection.close();
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
            Properties props = new Properties();
            // URL propertiesFile = currentThread().getContextClassLoader().getResource("datasource.properties");
            // FileInputStream jdbcConfig = new FileInputStream(propertiesFile.getFile());
            FileInputStream jdbcConfig = new FileInputStream("src/main/resources/datasource.properties");
            props.load(jdbcConfig);
            jdbcConfig.close();

            String driver = props.getProperty("jdbc.driver");
            if (driver != null) {
                Class.forName(driver);
            }
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection established");
        } catch (Exception e) {
            System.err.println("Cannot connect to database server");
            if (conn != null) {
                conn.close();
                System.out.println("Database connection terminated");
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
