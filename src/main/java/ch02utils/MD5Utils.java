/*
 * Created on 13-5-21
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2013 the original author or authors.
 */
package ch02utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字符串MD5转换
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-5-21
 */
public class MD5Utils {

    /**
     * @param str
     * @return
     * @Date: 2013-9-6
     * @Author: XiongNeng
     * @Description: 32位小写MD5
     */
    public static String parseStrToMd5L32(String str) {
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuilder stringBuffer = new StringBuilder();
            for (byte b : bytes) {
                int bt = b & 0xff;
                // 补0操作，比如byte=6的时候toHexString=6，前面要补一个0
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    public static String parseStrToMd5L32_(String source) {
        String s = null;
        char hexChar[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes());// 使用指定的byte数组更新摘要
            byte[] hashCalc = md.digest();// 完成哈希计算, 128/8 = 16，字节数组为16位长度
            char result[] = new char[16 * 2];// MD5结果返回的是32位字符串，每位是16进制表示的
            int k = 0;
            // 循环16次，对每个字节进行操作转换
            for (int i = 0; i < 16; i++) {
                byte everyByte = hashCalc[i];
                // 对每个字节的高4位进行处理，逻辑右移，再相与低4位转换
                result[k++] = hexChar[everyByte >>> 4 & 0xf];
                result[k++] = hexChar[everyByte & 0xf];
            }
            s = new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * @param str
     * @return
     * @Date: 2013-9-6
     * @Author: XiongNeng
     * @Description: 32位大写MD5
     */
    public static String parseStrToMd5U32(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.toUpperCase();
        }
        return reStr;
    }

    /**
     * @param str
     * @return
     * @Date: 2013-9-6
     * @Author: XiongNeng
     * @Description: 16位大写MD5
     */
    public static String parseStrToMd5U16(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.toUpperCase().substring(8, 24);
        }
        return reStr;
    }

    /**
     * @param str
     * @return
     * @Date: 2013-9-6
     * @Author: XiongNeng
     * @Description: 16位小写MD5
     */
    public static String parseStrToMd5L16(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.substring(8, 24);
        }
        return reStr;
    }
}
