package chapter01_basic.xstream;

import com.thoughtworks.xstream.XStream;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-24
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates
 */
public class Test<T> {
    /**
     * 关键字
     */
    String keyword;
    /**
     * 等级（debug、info、warn、error、fatal）
     */
    int level;
    /**
     * 记录类型：xml、text
     */
    String msgType = MSG_TYPE_TEXT;
    /**
     * 记录内容
     */
    String msgContent;
    /**
     * 消息实体
     */
    T msgEntity;

    public Test(String keyword, int level, String msgType, T msgEntity) {
        this.keyword = keyword;
        this.level = level;
        this.msgType = msgType;
        this.msgEntity = msgEntity;
        XStream xstream = new XStream();
        this.msgContent = xstream.toXML(msgEntity);
    }

    T getMsgEntity() {
        T t;
        if (MSG_TYPE_XML.equals(msgType)) {
            XStream xstream = new XStream();
            t = (T) xstream.fromXML(msgContent);
        } else {
            t = (T) msgContent;
        }
        return t;
    }

    /**
     * 消息等级
     */
    private static final int LEVEL_DEBUG = 1;
    private static final int LEVEL_INFO = 2;
    private static final int LEVEL_WARN = 3;
    private static final int LEVEL_ERROR = 4;
    private static final int LEVEL_FATAL = 5;
    /**
     * 消息类型，默认text
     */
    private static final String MSG_TYPE_TEXT = "text";
    private static final String MSG_TYPE_XML = "xml";

    public static void main(String[] args) {
        Person logRecord = new Person("dd", "ddd");
        Test<Person> logCommon = new Test<Person>("keyword", LEVEL_DEBUG, MSG_TYPE_TEXT, logRecord);
        Person record = logCommon.getMsgEntity();
    }
}
