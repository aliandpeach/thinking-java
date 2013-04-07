package chapter09_samples.jdbc.bonecp;

import com.jolbox.bonecp.BoneCPDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-26
 * Time: 下午4:46
 * To change this template use File | Settings | File Templates
 */
public class JdbcUtil {

    private static Logger log = LoggerFactory.getLogger(JdbcUtil.class);
    private static BoneCPDataSource dataSource;
    public static Properties props;

    /**
     * 初始化连接池(手动方式)
     *
     * @throws Exception
     */
    public static void initDataSource() throws Exception {
        BufferedReader ips = null;
        try {
            if (dataSource == null) {
                log.debug("The first time to init origin pool");
                props = new Properties();
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties");
                if (is != null) {
                    ips = new BufferedReader(new InputStreamReader(is));
                }
                props.load(ips);
                // load the DB driver
                Class.forName(props.getProperty("jdbc.driverClassName"));
                // create a new datasource object
                dataSource = new BoneCPDataSource();
                // set the JDBC url
                dataSource.setJdbcUrl(props.getProperty("jdbc.url"));
                // set the username
                dataSource.setUsername(props.getProperty("jdbc.username"));
                // set the password
                dataSource.setPassword(props.getProperty("jdbc.password"));
                dataSource.setIdleConnectionTestPeriodInMinutes(Long.parseLong(props.getProperty("jdbc.idleConnectionTestPeriodInMinutes")));
                dataSource.setIdleMaxAgeInMinutes(Long.parseLong(props.getProperty("jdbc.idleMaxAgeInMinutes")));
                dataSource.setMaxConnectionsPerPartition(Integer.parseInt(props.getProperty("jdbc.maxConnectionsPerPartition")));
                dataSource.setMinConnectionsPerPartition(Integer.parseInt(props.getProperty("jdbc.minConnectionsPerPartition")));
                dataSource.setPartitionCount(Integer.parseInt(props.getProperty("jdbc.partitionCount")));
                dataSource.setAcquireIncrement(Integer.parseInt(props.getProperty("jdbc.acquireIncrement")));
                dataSource.setStatementsCacheSize(Integer.parseInt(props.getProperty("jdbc.statementsCacheSize")));
                dataSource.setReleaseHelperThreads(Integer.parseInt(props.getProperty("jdbc.releaseHelperThreads")));
                dataSource.setDisableJMX(Boolean.parseBoolean(props.getProperty("jdbc.disableJMX")));
            }
        } finally {
            if (ips != null) ips.close();
        }
    }

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     * @throws Exception
     */
    public static Connection getConnection(){
        try {
            return (dataSource != null) ? dataSource.getConnection() : null;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("初始化数据库时失败=> ", e);
            return null;
        }
    }

    /**
     * 关闭数据库连接池
     *
     * @throws Exception
     */
    public static void closeDataSource() {
        dataSource.close();
    }
}
