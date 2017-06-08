package ch17database;

import java.util.*;
import java.sql.*;
import java.net.*;

/**
 * JSP页面访问Oracle数据库
 * <%@ page language="java" import="java.util.*,java.sql.*,java.net.*" pageEncoding="UTF-8" %>
 *
 */
public class OracleDB {
    /**
     * 环境变量设置
     * ORACLE_URL=10.10.112.171:49161
     * ORACLE_USER=system
     * ORACLE_PASSWORD=oracle
     * @throws Exception IO Ex
     */
    public static final void countVisitor() throws Exception {
        Map<String, String> p = System.getenv();
        Connection connection = null;
        PreparedStatement ps = null;
        String url = "jdbc:oracle:thin:@" + p.get("ORACLE_URL") + ":XE";//数据库名称就是你的数据库名字
        String driver = "oracle.jdbc.driver.OracleDriver"; //驱动类位置
        String username = p.get("ORACLE_USER");  //数据库登录名称,此处写上你的用户名称
        String pwd = p.get("ORACLE_PASSWORD");       //数据库登录密码,此处写上你的登录密码
        Integer countPage = 0;
        System.out.println(url);
        System.out.println(username);
        System.out.println(pwd);
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, pwd); //创建Connection连接对象
            // 先创建表
            try {
                ps = connection.prepareStatement("select count(*) from t_count");
                ps.executeQuery();
                ps.close();
            } catch (Exception ee) {
                System.out.println("First create t_count table...");

                ps = connection.prepareStatement("CREATE TABLE t_count (id number(10) NOT NULL, ccount number(10) DEFAULT 0)");
                ps.executeUpdate();
                ps.close();

                ps = connection.prepareStatement("insert into t_count values (1, 0)");
                ps.executeUpdate();
                ps.close();
            }

            ps = connection.prepareStatement("update t_count set ccount=ccount+1");
            ps.executeUpdate();
            ps.close();

            ps = connection.prepareStatement("select ccount from t_count WHERE ROWNUM = 1");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                countPage = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (ps != null) ps.close();
            if (connection != null) connection.close();
        }
//        out.println("您是第" + countPage + "位访问客户！");        //前台页面输出
//        out.println("IP地址：" + InetAddress.getLocalHost().getHostAddress());        //前台页面输出
    }
}
