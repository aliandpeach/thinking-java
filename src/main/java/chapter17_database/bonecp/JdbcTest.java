/*
 * Created on 12-8-13
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
 * Copyright @2012 the original author or authors.
 */
package chapter17_database.bonecp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description of this file.
 * User: Administrator
 * Date: 12-8-13
 * Time: 下午3:19
 */
public class JdbcTest {
    private static Logger log = LoggerFactory.getLogger(JdbcTest.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    private final String clear_sql = "DELETE FROM test";
    private final String insert_sql = "INSERT INTO test(thread_id) VALUES(?)";
    private final String select_sql = "SELECT thread_id, count(*) as insert_count from test GROUP  BY thread_id";
    private static int finishCount = 0;
    private List<Long> idList;

    public static void main(String[] args) throws Exception {
        run();
    }

    private static void run() throws Exception {
        log.info("开始时间：" + sdf.format(new Date()));
        JdbcUtil.initDataSource();
        final int threadNum = Integer.parseInt(JdbcUtil.props.getProperty("insert.threads"));
        final int insertNum = Integer.parseInt(JdbcUtil.props.getProperty("insert.times"));
        final JdbcTest jdbcTest = new JdbcTest();
        jdbcTest.idList = new ArrayList<Long>(insertNum * threadNum);
        jdbcTest.doClearData(JdbcUtil.getConnection());
        log.info("===开始插入大量数据=== " + sdf.format(new Date()));
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    jdbcTest.doInsert(JdbcUtil.getConnection(), insertNum);
                }
            }).start();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (finishCount != threadNum) {
                    try {
                        Thread.sleep(10 * 1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                finishCount = 0;
                log.info("===结束插入大量数据=== " + sdf.format(new Date()));
                // jdbcTest.analyseResult(JdbcUtil.getConnection(), insertNum);
                // 开始分析数据
                jdbcTest.writeAnalyseFile();
            }
        }).start();
    }

    private void doClearData(Connection connection) {
        log.info("===开始清除表中的数据=== " + sdf.format(new Date()));
        try {
            Statement pstmt = connection.createStatement();
            pstmt.execute(clear_sql);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("清除数据时候错误 " + sdf.format(new Date()), e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("===清除数据时候错误=== " + sdf.format(new Date()), e);
            }
        }
        log.info("===结束清除表中的数据=== " + sdf.format(new Date()));
    }

    private void doInsert(Connection connection, int times) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(insert_sql, Statement.RETURN_GENERATED_KEYS);
            // PreparedStatement pstmt = connection.prepareStatement(insert_sql);
            for (int i = 0; i < times; i++) {
                pstmt.setLong(1, Thread.currentThread().getId());
                pstmt.executeUpdate();
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    // 知其仅有一列，故获取第一列
                    synchronized (this) {
                        idList.add(rs.getLong(1));
                    }
                }
                // pstmt.addBatch();
            }
            // pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("数据库错误 | 线程ID为:" + Thread.currentThread().getId() + sdf.format(new Date()), e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("数据库错误 | 线程ID为:" + Thread.currentThread().getId() + sdf.format(new Date()), e);
            }
        }
        synchronized (this) {
            finishCount++;
        }
    }

    /**
     * @param connection
     * @param insertNum
     */
    private void analyseResult(Connection connection, int insertNum) {
        log.info("===开始分析结果数据了=== " + sdf.format(new Date()));
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select_sql);
            while (rs.next()) {
                String threadId = rs.getString(1);
                long count = rs.getLong(2);
                if (count != insertNum) {
                    throw new Exception("主键ID冲突, 线程号为:" + threadId);
                }
            }
            log.info("===结束分析结果数据，没问题=== " + sdf.format(new Date()));
            log.info("结束时间：" + new Date());
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("数据库错误 | 线程ID为:" + Thread.currentThread().getId() + sdf.format(new Date()), e);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("主键ID冲突错误 | 线程ID为:" + Thread.currentThread().getId() + sdf.format(new Date()), e);
        } finally {
            try {
                connection.close();
                JdbcUtil.closeDataSource();
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("关闭连接错误 | 线程ID为:" + Thread.currentThread().getId() + sdf.format(new Date()), e);
            }
        }
    }

    private void writeAnalyseFile() {
        log.info("===开始写id到文件中=== " + sdf.format(new Date()));
        PrintWriter out = null;
        try {
            String idFile = JdbcUtil.props.getProperty("analyse.id.file");
            out = new PrintWriter(new BufferedWriter(new FileWriter(idFile)));
            for (Long id : idList) {
                out.println(id);
            }
            out.flush();
            out.close();
            log.info("===结束写id到文件中=== " + sdf.format(new Date()));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("===写id到文件时出错=== " + sdf.format(new Date()), e);
        } finally {
            if (out != null) {
                out.close();
            }
            log.info("结束时间：" + sdf.format(new Date()));
        }
    }
}
