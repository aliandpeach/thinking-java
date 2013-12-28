package chapter01_basic.xstream;

import com.thoughtworks.xstream.XStream;

/**
 * log记录
 */
class LogCommon<T> {
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
    /**
     * xml序列化
     */
    XStream xstream = new XStream();

    LogCommon(String keyword, int level, String msgType, String msgContent, T msgEntity) {
        this.keyword = keyword;
        this.level = level;
        this.msgType = msgType;
        this.msgEntity = msgEntity;
        if (MSG_TYPE_XML.equals(msgType)) {
            this.msgContent = xstream.toXML(msgEntity);
        } else {
            this.msgContent = msgContent;
        }
    }

    T getMsgEntity() {
        T t;
        if (MSG_TYPE_XML.equals(msgType)) {
            t = (T) xstream.fromXML(msgContent);
        } else {
            t = (T) msgContent;
        }
        return t;
    }

    /**
     * 消息等级
     */
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARN = 3;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_FATAL = 5;
    /**
     * 消息类型，默认text
     */
    public static final String MSG_TYPE_TEXT = "text";
    public static final String MSG_TYPE_XML = "xml";
}
