package org.cneng.httpclient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/6/11
 */
public class DateUtil {

    private static final SimpleDateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日");

    public static Date toDate(String str) {
        try {
            return df1.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String toStr(Date d) {
        return d == null ? "" : df1.format(d);
    }
}
