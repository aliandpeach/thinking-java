package org.cneng.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义日期反序列化
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/6/14
 */
public class MyDateDeserialize {
    private static ParserConfig parseConfig = new ParserConfig();

    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        parseConfig.putDeserializer(Date.class, new MyDateFormatDeserializer(dateFormat));
    }

    public static <T> T myParseObject(String jsonStr, Class<T> t) {
        return JSON.parseObject(jsonStr, t, parseConfig,
                JSON.DEFAULT_PARSER_FEATURE, new Feature[0]);
    }

    public static <T> T myParseObject(String jsonStr, String dateFormat, Class<T> t) {
        ParserConfig parseConfig1 = new ParserConfig();
        parseConfig1.putDeserializer(Date.class, new MyDateFormatDeserializer(dateFormat));
        return JSON.parseObject(jsonStr, t, parseConfig1,
                JSON.DEFAULT_PARSER_FEATURE, new Feature[0]);
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, ParseException {
        String json = "{\"name\":\"22323\", \"age\": 1234," +
                " \"birthday\": \"2012-12/12 12:12:12\"}";
        User t = myParseObject(json, "yyyy-MM/dd HH:mm:ss", User.class);
        System.out.println(t.name);
        System.out.println(t.height);
        System.out.println(t.birthday);
    }
}
