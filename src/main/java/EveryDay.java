/*
 * Created on 13-8-8
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2013 the original author or authors.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-8-8
 */
public class EveryDay {
    private static final String SELECT_SQL = "SELECT DISTINCT order_info.biz_transaction_id bizId," +
            " insurance_application.effect_date effectDate" +
            " FROM " +
            " order_payment_info," +
            " order_info," +
            " insurance_application" +
            " WHERE " +
            " order_info.biz_transaction_id=insurance_application.biz_transaction_id" +
            " AND" +
            " order_info.id=order_payment_info.order_id" +
            " AND" +
            " order_info.payment_status<>'PAI'" +
            " AND" +
            " order_payment_info.payment_method='Mobile99bill'" +
            " AND order_payment_info.payee=order_payment_info.payment_target" +
            " AND order_payment_info.`status`='Avail'" +
            " AND insurance_application.effect_date<NOW()" +
            " ORDER BY insurance_application.effect_date DESC";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {
        String outFile = args[0];
        System.out.println("---" + outFile);
        String url = "jdbc:mysql://121.14.57.226:33060/b2b_biz1?useUnicode=true&amp;characterEncoding=UTF-8";
        String username = "zenglb01";
        String password = "zenglb123";
        Connection cn = null;
        Statement stm = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection(url, username, password);
            stm = cn.createStatement();
            ResultSet rs = stm.executeQuery(SELECT_SQL);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
            while (rs.next()) {
                String bizId = rs.getString("bizId");
                String effectDate = rs.getString("effectDate");
                System.out.println(bizId + effectDate);
                writer.append(bizId).append(",").append(effectDate);
                writer.newLine();
            }
            writer.flush();
            writer.close();

        } finally {
            if (stm != null) stm.close();
            if (cn != null) cn.close();
        }
    }
}
