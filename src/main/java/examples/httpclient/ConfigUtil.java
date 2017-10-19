package examples.httpclient;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/2/3
 */

public class ConfigUtil {
    private static ConfigUtil initor = new ConfigUtil();

    private PropertiesConfiguration config;

    private ConfigUtil() {
        initConfig("checkcode.properties");
    }
    public static ConfigUtil getInstance() {
        return initor;
    }

    /**
     * 获取内容
     *
     * @param property pro
     * @return result
     */
    public static String get(String property) {
        return initor.config.getString(property);
    }

    /**
     * 载入配置文件，初始化后加入map
     *
     * @param configFile file
     */
    private synchronized void initConfig(String configFile) {
        try {
            config = new PropertiesConfiguration(configFile);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
