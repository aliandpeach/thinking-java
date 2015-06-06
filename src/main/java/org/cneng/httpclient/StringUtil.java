package org.cneng.httpclient;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang.StringUtils;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * 字符串工具类
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/2/28
 */
public class StringUtil {

    /**
     * 将null转换成空字符串
     *
     * @param str 字符串
     * @return 结果字符串
     */
    public static String nullToEmpty(String str) {
        return str == null ? "" : str;
    }

    /**
     * 将null转换成空字符串
     *
     * @param str 字符串
     * @return 结果字符串
     */
    public static String nullToEmpty1(String str, String otherNull) {
        return (str == null || str.equals(otherNull)) ? "" : str;
    }

    /**
     * 判断字符串是否为空字符
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    /**
     * 判断字符串是否不为空字符
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    /**
     * 获取文件的UTF-8编码字符串表示
     *
     * @param filename 文件名
     * @return 文件内容
     */
    private static String getUTF8(String filename) {
        String content = null;
        String charset = "utf-8";
        try {
            FileInputStream fis = new FileInputStream(filename);
            //可检测多种类型，并剔除bom
            BOMInputStream bomIn = new BOMInputStream(fis, false, ByteOrderMark.UTF_8);
            //若检测到bom，则使用bom对应的编码
            if (bomIn.hasBOM()) {
                charset = bomIn.getBOMCharsetName();
            }
            InputStreamReader reader = new InputStreamReader(bomIn, charset);
            content = new Scanner(reader).useDelimiter("\\Z").next();
            System.out.println(content);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 根据推荐状态来获取是否可增加新推荐
     *
     * @param recommendStatus 推荐状态
     * @return 是否可编辑
     */
    public static boolean addFlagByStatus(String recommendStatus) {
        return "待推荐".equals(recommendStatus)
                || "推荐失败".equals(recommendStatus)
                || "审核不通过".equals(recommendStatus);
    }

    /**
     * 根据推荐状态来获取是否可保存
     *
     * @param recommendStatus 推荐状态
     * @return 是否可编辑
     */
    public static boolean saveFlagByStatus(String recommendStatus) {
        return "推荐中".equals(recommendStatus);
    }

    private static DecimalFormat df = new DecimalFormat("0");

    public static String floatS(Float f) {
        return df.format(f);
    }

    public static String doubleS(Double d) {
        return df.format(d);
    }
}
