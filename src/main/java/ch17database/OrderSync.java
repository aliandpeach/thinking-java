package ch17database;

import net.mindview.util.Sets;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 订单同步
 * 命令行运行Java程序：
 * .表示当前目录，多个点jar包用:分隔开来
 * export CLASSPATH=.:$CLASSPATH
 * java -cp .:./mysql-connector.jar OrderSync -database.0 mydb -dbname.0 mydb
 */
public class OrderSync {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {
        printDiff();
//        List<Order> orderList = prepareData();
//        saveOrders(orderList);
//        Pattern pattern = Pattern.compile("^.*\"re_openid\":\"(.*?)\".*\"total_amount\":\"(.*?)\".*$");
//        String a = "2017-06-01 11:58:47 {\"re_openid\":\"oo38ov05CIYjF8IJhyDQggjRIa-s\",\"total_amount\":\"880\",\"err_code\":\"SUCCESS\",\"return_msg\":\"发放成功\",\"result_code\":\"SUCCESS\",\"err_code_des\":\"发放成功\",\"mch_id\":\"1293290901\",\"send_listid\":\"1000041701201706013000057759193\",\"return_code\":\"SUCCESS\",\"wxappid\":\"wx9abbb2bf93e4f1d7\",\"mch_billno\":\"chongqing6482094\"}";
//        Matcher matcher = pattern.matcher(a);
//        if (matcher.matches()) {
//            System.out.println(matcher.group(0));
//            System.out.println(matcher.group(1));
//            System.out.println(matcher.group(2));
//        }
    }

    private static void printDiff() throws Exception {
        Set<String> dborderidSet = new HashSet<>();
        Set<String> wxorderidSet = new HashSet<>();
        BufferedReader s = null;
        BufferedReader s2 = null;
        try {
            s = new BufferedReader(new InputStreamReader(
                    new FileInputStream("D:/download/order.txt"), "UTF-8"));
            String tmp = s.readLine();
            while (tmp != null && !"".equals(tmp.trim())) {
                tmp = tmp.trim();
                dborderidSet.add(tmp);
                tmp = s.readLine();
            }
            s2 = new BufferedReader(new InputStreamReader(
                    new FileInputStream("D:/download/chongqing.csv"), "UTF-8"));
            String tmp2 = s2.readLine();
            while (tmp2 != null && !"".equals(tmp2.trim())) {
                tmp2 = tmp2.trim();
                String[] strlist = tmp2.split("：");
                wxorderidSet.add(strlist[1]);
                tmp2 = s2.readLine();
            }

            System.out.println("-------------最后开始比较-----------------");
            System.out.println("数据库中订单数量：" + dborderidSet.size());
            System.out.println("微信中订单数量：" + wxorderidSet.size());
            System.out.println("在db而不在wx中的订单号：dborderidSet - wxorderidSet："
                    + Sets.difference(dborderidSet, wxorderidSet));
            Set<String> diffSet =  Sets.difference(dborderidSet, wxorderidSet);
            for (String df : diffSet) {
                if (df.startsWith("chongqing")) {
                    System.out.println("findg............" + df);
                }
            }
            System.out.println("在wx而不在db中的订单号：wxorderidSet - dborderidSet："
                    + Sets.difference(wxorderidSet, dborderidSet));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (s != null) s.close();
                if (s2 != null) s2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Order> prepareData()  throws Exception {
        List<Order> resultList = new ArrayList<>();
        Set<String> dborderidSet = new HashSet<>();
        BufferedReader s = null;
        BufferedReader s2 = null;
        try {
//            s = new BufferedReader(new InputStreamReader(
//                    new FileInputStream("D:/download/order.txt"), "UTF-8"));
            s = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/root/order.txt"), "UTF-8"));
            String tmp = s.readLine();
            while (tmp != null) {
                dborderidSet.add(tmp);
                tmp = s.readLine();
            }
//            s2 = new BufferedReader(new InputStreamReader(
//                    new FileInputStream("D:/download/logs.txt"), "UTF-8"));
            s2 = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/root/logs.txt"), "UTF-8"));
            String tmp2 = s2.readLine();
            Pattern pattern = Pattern.compile("^.*\"mch_billno\":\"(.*?)\".*$");
            Pattern pattern2 = Pattern.compile("^.*\"re_openid\":\"(.*?)\".*\"total_amount\":\"(.*?)\".*$");
            while (tmp2 != null) {
                tmp2 = tmp2.trim();
                String[] strlist = tmp2.split(" ");
                String logTime = strlist[0] + " " + strlist[1];
//                System.out.println("logTime=" + logTime);
                Matcher matcher = pattern.matcher(tmp2);
                if (matcher.matches()) {
                    String orderid = matcher.group(1);
                    if (! dborderidSet.contains(orderid)) {
                        Matcher matcher2 = pattern2.matcher(tmp2);
                        if (matcher2.matches()) {
                            Order order = new Order();
                            String openid = matcher2.group(1);
                            Integer amount = Integer.valueOf(matcher2.group(2));
                            System.out.println("需要更新的：openid=" + openid + ",amount=" + amount + ",orderid=" + orderid);
                            String[] pc = getPhoneChannel(openid);
                            order.setPhone(pc[0]);
                            order.setChannelId(Long.parseLong(pc[1]));
                            order.setLuckyMoney(amount);
                            order.setOrderid(orderid);
                            order.setOpenid(openid);
                            order.setCreatedTime(logTime);
                            order.setUpdatedTime(logTime);
                            resultList.add(order);
                        }
                    }
                }
                tmp2 = s2.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (s != null) s.close();
                if (s2 != null) s2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }
    private static final String querySql = "select phone, channel_id from t_lottery where openid=? LIMIT 1";
    private static String[] getPhoneChannel(String openid) throws Exception {
        String[] result = new String[2];
        Connection connection = getConnection();
        assert connection != null;
        PreparedStatement ps = connection.prepareStatement(querySql);
        ps.setString(1, openid);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            result[0] = rs.getString("phone");
            result[1] = rs.getString("channel_id");
        } else {
            result[0] = "18800000000";
            result[1] = "1";
        }
        rs.close();
        ps.close();
        connection.close();
        return result;
    }
    private static final String selectOrderCount = "select count(*) from t_lottery_order where orderid=?";
    private static final String insertOrderSql = "insert into t_lottery_order(channel_id,orderid,phone," +
            "lucky_money,created_time,updated_time) values (?,?,?,?,?,?)";

    private static final String selectLotteryCount = "select count(*) from t_lottery where openid=? and phone=? and channel_id=?";
    private static final String insertLotterySql = "INSERT INTO t_lottery(openid,phone,channel_id,active,start," +
            "lucky_count,lucky_money,scan_count_total,lucky_count_total,lucky_money_total,valid,created_time,updated_time)" +
            " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
    private static final String updateLotterySql = "update t_lottery set " +
            "active=0,start=0,lucky_count=0,lucky_money=0,scan_count_total=scan_count_total+1," +
            "lucky_count_total=5,lucky_money_total=lucky_money_total+?,created_time=?," +
            "updated_time=? where openid=? and phone=? and channel_id=?";
    private static void saveOrders(List<Order> list) throws Exception {
        Connection connection = getConnection();
        assert connection != null;
        int countIndex = 1;
        for (Order order: list) {
            System.out.println("开始更新第" + countIndex + "条订单和抽奖记录...");
            countIndex++;

            // 查询订单表看是否存在
            PreparedStatement ps = connection.prepareStatement(selectOrderCount);
            ps.setString(1, order.getOrderid());
            ResultSet rs = ps.executeQuery();
            int orderc = 0;
            if (rs.next()) {
                orderc = rs.getInt(1);
            }
            rs.close();
            ps.close();
            if (orderc > 0) {
                continue;
            }
            // 开始插入订单
            ps = connection.prepareStatement(insertOrderSql);
            ps.setLong(1, order.getChannelId());
            ps.setString(2, order.getOrderid());
            ps.setString(3, order.getPhone());
            ps.setInt(4, order.getLuckyMoney());
            ps.setString(5, order.getCreatedTime());
            ps.setString(6, order.getUpdatedTime());
            ps.executeUpdate();
            ps.close();

            // 查询抽奖表看是否存在
            ps = connection.prepareStatement(selectLotteryCount);
            ps.setString(1, order.getOpenid());
            ps.setString(2, order.getPhone());
            ps.setLong(3, order.getChannelId());
            rs = ps.executeQuery();
            int lcount = 0;
            if (rs.next()) {
                lcount = rs.getInt(1);
            }
            rs.close();
            ps.close();

            if (lcount == 0) {
                // 没有就插入这条记录
                ps = connection.prepareStatement(insertLotterySql);
                ps.setString(1, order.getOpenid());
                ps.setString(2, order.getPhone());
                ps.setLong(3, order.getChannelId());
                ps.setInt(4, 0);
                ps.setInt(5, 0);
                ps.setInt(6, 0);
                ps.setInt(7, 0);
                ps.setInt(8, 1);
                ps.setInt(9, 5);
                ps.setInt(10, order.getLuckyMoney());
                ps.setInt(11, 1);
                ps.setString(12, order.getCreatedTime());
                ps.setString(13, order.getUpdatedTime());
                ps.executeUpdate();
                ps.close();
            } else {
                // 有就更新这条记录
                ps = connection.prepareStatement(updateLotterySql);
                ps.setInt(1, order.getLuckyMoney());
                ps.setString(2, order.getCreatedTime());
                ps.setString(3, order.getUpdatedTime());
                ps.setString(4, order.getOpenid());
                ps.setString(5, order.getPhone());
                ps.setLong(6, order.getChannelId());
                ps.executeUpdate();
                ps.close();
            }
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
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/tobacco?useUnicode=true&characterEncoding=utf8";
//            String url = "jdbc:mysql://192.168.212.200:3306/tobacco?useUnicode=true&characterEncoding=utf8";
            String username = "root";
            String password = "vs_XNbaobao122";
//            String password = "winstore";
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

    private static class Order {
        /**
         * 订单号
         */
        private String orderid;
        /**
         * 渠道ID
         */
        private Long channelId;
        /**
         * openid
         */
        private String openid;
        /**
         * 用户手机号
         */
        private String phone;
        /**
         * 用户本次抽奖金额（分）
         */
        private Integer luckyMoney;
        /**
         * 创建时间
         */
        private String createdTime;
        /**
         * 更新时间
         */
        private String updatedTime;

        String getOpenid() {
            return openid;
        }

        void setOpenid(String openid) {
            this.openid = openid;
        }

        String getCreatedTime() {
            return createdTime;
        }

        void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        String getUpdatedTime() {
            return updatedTime;
        }

        void setUpdatedTime(String updatedTime) {
            this.updatedTime = updatedTime;
        }

        Long getChannelId() {
            return channelId;
        }

        void setChannelId(Long channelId) {
            this.channelId = channelId;
        }

        String getOrderid() {
            return orderid;
        }

        void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        String getPhone() {
            return phone;
        }

        void setPhone(String phone) {
            this.phone = phone;
        }

        Integer getLuckyMoney() {
            return luckyMoney;
        }

        void setLuckyMoney(Integer luckyMoney) {
            this.luckyMoney = luckyMoney;
        }
    }
}
