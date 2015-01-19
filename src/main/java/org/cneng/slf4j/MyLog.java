package org.cneng.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-6-29
 * Time: 上午11:25
 * SlF4j + Log4J Test
 */
public class MyLog {
    private static final Logger logger = LoggerFactory.getLogger(MyLog.class);

    public static void main(String[] args) {
        myLog();
    }

    /**
     * 一般用法
     */
    public static void commonLog() {
        // 文件的信息等级为默认的DEBUG，会出现全部三个信息：debug,info,warn信息
        // 控制台的信息等级INFO，会出现info信息，warn信息
        // 数据库的信息等级WARN，会出现warn信息
        Order order = new Order(1111L, "这是一个订单", "张三丰", new BigDecimal(1876.98));
        logger.debug("debug信息：订单号id为{},详细的信息为：{}", order.getId(), order);
        logger.info("info信息：订单号id为{},详细的信息为：{}", order.getId(), order);
        logger.warn("warn信息：订单号id为{},详细的信息为：{}", order.getId(), order);
        logger.error("error信息：订单号id为{},详细的信息为：{}", order.getId(), order);
    }

    /**
     * 自定义用法
     */
    public static void myLog() {
        Logger myLogger = LoggerFactory.getLogger("com.springzoo.MYLOG");
        myLogger.debug("debug信息：自定义logger，没有设置additivity属性 = =");
        myLogger.info("info信息：自定义logger，没有设置additivity属性 = =");
        myLogger.warn("warn信息：自定义logger，没有设置additivity属性 = =");
        myLogger.error("error信息：自定义logger，没有设置additivity属性 = =");
    }

    public static void logError() {
        logger.info("===================");
        logger.error("出错了！！！", new Exception("Nullpoint Exception"));
    }
}
