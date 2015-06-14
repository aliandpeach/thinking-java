package org.cneng.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.util.Date;

/**
 * 自定义日期序列化
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/6/14
 */
public class MyDateSerialize {
    private static SerializeConfig mapping = new SerializeConfig();

    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
    }

    /**
     * 默认的处理时间
     *
     * @param jsonText
     * @return
     */
    public static String toJSON(Object obj) {
        return JSON.toJSONString(obj,
                SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 自定义时间格式
     *
     * @param jsonText
     * @return
     */
    public static String toJSON(String dateFormat, Object obj) {
        return JSON.toJSONString(obj, mapping);
    }


}
