package examples.fastjson;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.DateFormatDeserializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 自定义反序列化解析类
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/6/14
 */
public class MyDateFormatDeserializer extends DateFormatDeserializer {

    private String myFormat;

    public MyDateFormatDeserializer(String myFormat) {
        super();
        this.myFormat = myFormat;
    }

    @Override
    protected <Date> Date cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val) {
        if (myFormat == null) {
            return null;
        }
        if (val instanceof String) {
            String strVal = (String) val;
            if (strVal.length() == 0) {
                return null;
            }
            try {
                return (Date) new SimpleDateFormat(myFormat).parse((String) val);
            } catch (ParseException e) {
                throw new JSONException("parse error");
            }
        }
        throw new JSONException("parse error");
    }
}