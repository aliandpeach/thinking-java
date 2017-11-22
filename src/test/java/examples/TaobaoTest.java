package examples;

import examples.httpclient.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTimeUtils;
import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TaobaoTest
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2017/11/22
 */
public class TaobaoTest {
    /**
     * http://gw.api.tbsandbox.com/router/rest?
     * sign=31D55B058B138B9216BAA7B484B22FC4
     * &timestamp=2017-11-22+16%3A29%3A01
     * &v=2.0
     * &app_key=1024676687
     * &method=taobao.opensecurity.uid.get
     * &partner_id=top-apitools
     * &format=json
     * &tb_user_id=123456
     * &force_sensitive_param_fuzzy=true
     */
    @Test
    public void testTaobao1() throws Exception {
        // taobao.ma.barcode.productinfo.get (通过条码查询产品信息)
        Map<String, String> params = new HashMap<>();
        params.put("method", "taobao.ma.barcode.productinfo.get");
        params.put("app_key", "1024676687");
        params.put("sign_method", "md5");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("format", "json");
        params.put("v", "2.0");
        params.put("barcode", "6951439909683");
        String sign = signRequest(params, "sandbox72d7ed12a1360b7074ce5750f", "md5");
        params.put("sign", sign);
        List<NameValuePair> list = new ArrayList<>();
        for (String k : params.keySet()) {
            list.add(new BasicNameValuePair(k, params.get(k)));
        }
        System.out.println("http://gw.api.tbsandbox.com/router/rest?" + URLEncodedUtils.format(list, Charset.forName("UTF-8")));
    }

    @Test
    public void testTaobao2() throws Exception {
        // taobao.ma.barcode.productinfo.get (通过条码查询产品信息)
        // 公共参数
        Map<String, String> params = new HashMap<>();
        params.put("method", "taobao.ma.barcode.productinfo.get");
        params.put("app_key", "24676687");
        params.put("sign_method", "md5");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("format", "json");
        params.put("v", "2.0");
        // 业务参数
        params.put("barcode", "6951439909683");
        String sign = signRequest(params, "195cf2d72d7ed12a1360b7074ce5750f", "md5");
        params.put("sign", sign);
        List<NameValuePair> list = new ArrayList<>();
        for (String k : params.keySet()) {
            list.add(new BasicNameValuePair(k, params.get(k)));
        }
        System.out.println("http://gw.api.taobao.com/router/rest?" + URLEncodedUtils.format(list, Charset.forName("UTF-8")));
    }

    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";

    public static String signRequest(Map<String, String> params,
                                     String secret, String signMethod) throws Exception {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        if ("md5".equals(signMethod)) {
            query.append(secret);
        }
        for (String key : keys) {
            String value = params.get(key);
            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                query.append(key).append(value);
            }
        }

        // 第三步：使用MD5/HMAC加密
        byte[] bytes;
        if ("hmac".equals(signMethod)) {
            bytes = encryptHMAC(query.toString(), secret);
        } else {
            query.append(secret);
            bytes = encryptMD5(query.toString());
        }

        // 第四步：把二进制转化为大写的十六进制(正确签名应该为32大写字符串，此方法需要时使用)
        return byte2hex(bytes);
    }

    public static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    public static byte[] encryptMD5(String data) throws Exception {
        return encryptMD5(data.getBytes("UTF-8"));
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();
    }

    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);
        return sha.digest();
    }

    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

}


