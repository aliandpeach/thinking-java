package org.cneng.httpclient;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.Args;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import static org.cneng.httpclient.Utils.*;
import static org.cneng.httpclient.ConfigUtil.get;

/**
 * 工商局查询管理器
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/2/3
 */
public class QueryManager {
    /**
     * 验证码图片生成地址
     */
    private String verifyPicUrl = "http://gsxt.gdgs.gov.cn/verify.html";
    /**
     * 验证码服务器验证地址
     */
    private String checkCodeUrl = "http://gsxt.gdgs.gov.cn/CheckEntContext/checkCode.html";
    /**
     * 信息显示URL
     */
    private String showInfoUrl = "http://gsxt.gdgs.gov.cn/CheckEntContext/showInfo.html";
    /**
     * 下载的验证码图片文件夹
     */
    private String outdir = "D:/work/";

    private static final QueryManager instance = new QueryManager();

    public QueryManager() {
        ConfigUtil.getInstance();
        verifyPicUrl = get("verifyPicUrl");
        checkCodeUrl = get("checkCodeUrl");
        showInfoUrl = get("showInfoUrl");
        outdir = get("outdir");
    }

    /**
     * 工商局网站上面通过关键字搜索企业信息
     * @param keyword 关键字：注册号或者是企业名称
     * @return 企业地址
     */
    public String search(String keyword) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(sdf.format(new Date()));
            // 第一步：先下载验证码图片到本地
            String[] downloads = downloadVerifyPic();
            // 第二步：获取验证码
            String checkcode = CheckCodeClient.checkCode(downloads[0]);
            // JSESSIONID
            String jsessionid = downloads[1];
            // 第三步：调用服务器的验证码认证接口
            CheckCodeResult checkCodeResult = authenticate(keyword, checkcode, jsessionid);
            if ("1".equals(checkCodeResult.getFlag())) {
                String lastResult = showInfo(checkCodeResult.getTextfield(), checkcode, jsessionid);
            } else {
                System.out.println("-----------------error------------------");
            }
            System.out.println(sdf.format(new Date()));
            // ---------------------------------------------------------------------------------
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下载验证码图片
     * @return [下载图片地址, Cookie中的JSESSIONID]
     * @throws Exception
     */
    private String[] downloadVerifyPic() throws Exception {
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).build();
        CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        CloseableHttpClient httpclient = HttpClients.custom().
                setDefaultRequestConfig(globalConfig).setDefaultCookieStore(cookieStore).build();
        try {
            HttpGet httpget = new HttpGet(verifyPicUrl + "?random=" + Math.random());
            System.out.println("Executing request " + httpget.getRequestLine());
            // 所有请求的通用header：
            httpget.addHeader("Accept", "image/webp,*/*;q=0.8");

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? doDownload(entity, outdir) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String doawloadPic = httpclient.execute(httpget, responseHandler);
            List<Cookie> cookies = context.getCookieStore().getCookies();
            for (Cookie cookie : cookies) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    System.out.println(cookie.getName() + ":" + cookie.getValue());
                    System.out.println("******************************");
                    return new String[]{doawloadPic, cookie.getValue()};
                }
            }
        } finally {
            httpclient.close();
        }
        return null;
    }

    private String doDownload(final HttpEntity entity, String outdir) throws IOException {
        String fullname = outdir + randomUUID() + ".png";
        OutputStream outputStream = new FileOutputStream(new File(fullname));
        Args.notNull(entity, "Entity");
        final InputStream instream = entity.getContent();
        if (instream == null) {
            return null;
        }
        try {
            Args.check(entity.getContentLength() <= Integer.MAX_VALUE,
                    "HTTP entity too large to be buffered in memory");
            final byte[] buff = new byte[4096];
            int readed;
            while ((readed = instream.read(buff)) != -1) {
                byte[] temp = new byte[readed];
                System.arraycopy(buff, 0, temp, 0, readed);   // 这句是关键
                outputStream.write(temp);
            }
        } finally {
            instream.close();
            outputStream.close();
        }
        return fullname;
    }

    /**
     * 验证码服务器验证接口
     * @param keywork 关键字
     * @param checkcode 验证码
     * @param jsessionid jsessionid
     * @return 结果
     * @throws Exception
     */
    private CheckCodeResult authenticate(String keywork, String checkcode, String jsessionid) throws Exception {
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).build();
        CookieStore cookieStore = new BasicCookieStore();
//        cookieStore.addCookie(new BasicClientCookie("JSESSIONID", jsessionid));
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        CloseableHttpClient httpclient = HttpClients.custom().
                setDefaultRequestConfig(globalConfig).setDefaultCookieStore(cookieStore).build();
        try {
            HttpPost httppost = new HttpPost(checkCodeUrl);
            System.out.println("Executing request " + httppost.getRequestLine());

            // 所有请求的通用header：
            httppost.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httppost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36" +
                    " (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httppost.addHeader("Accept-Encoding", "gzip,deflate");
            httppost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
//            httppost.addHeader("Connection", "keep-alive");
//            httppost.addHeader("Cache-Control", "max-age=0");

            StringBody textfield = new StringBody(keywork, ContentType.TEXT_PLAIN);
            StringBody code = new StringBody(checkcode, ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("textfield", textfield)
                    .addPart("code", code)
                    .build();
            httppost.setEntity(reqEntity);
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httppost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return JSON.parseObject(responseBody, CheckCodeResult.class);
        } finally {
            httpclient.close();
        }
    }

    /**
     * 查询实际的结果
     * @param textfield 验证码验证后返回的关键字
     * @param checkcode 验证码
     * @param jsessionid sessionid
     * @return 查询页面
     * @throws Exception
     */
    private String showInfo(String textfield, String checkcode, String jsessionid) throws Exception {
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH).build();
        CookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(new BasicClientCookie("JSESSIONID", jsessionid));
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        CloseableHttpClient httpclient = HttpClients.custom().
                setDefaultRequestConfig(globalConfig).setDefaultCookieStore(cookieStore).build();
        try {
            HttpPost httppost = new HttpPost(checkCodeUrl);
            System.out.println("Executing request " + httppost.getRequestLine());

            // 所有请求的通用header：
            httppost.addHeader("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httppost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36" +
                    " (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.addHeader("Accept-Encoding", "gzip,deflate");
            httppost.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
//            httppost.addHeader("Connection", "keep-alive");

            StringBody textfield1 = new StringBody(textfield, ContentType.TEXT_PLAIN);
            StringBody code1 = new StringBody(checkcode, ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("textfield", textfield1)
                    .addPart("code", code1)
                    .build();
            httppost.setEntity(reqEntity);
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httppost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;
        } finally {
            httpclient.close();
        }
    }

    private static class CheckCodeResult {
        private String flag;
        private String textfield;
        private String tip;

        /**
         * 获取 textfield.
         *
         * @return textfield.
         */
        public String getTextfield() {
            return textfield;
        }

        /**
         * 获取 flag.
         *
         * @return flag.
         */
        public String getFlag() {
            return flag;
        }

        /**
         * 设置 textfield.
         *
         * @param textfield textfield.
         */
        public void setTextfield(String textfield) {
            this.textfield = textfield;
        }

        /**
         * 设置 flag.
         *
         * @param flag flag.
         */
        public void setFlag(String flag) {
            this.flag = flag;
        }

        /**
         * 设置 tip.
         *
         * @param tip tip.
         */
        public void setTip(String tip) {
            this.tip = tip;
        }

        /**
         * 获取 tip.
         *
         * @return tip.
         */
        public String getTip() {
            return tip;
        }
    }


    public static void main(String[] args) throws Exception {
        instance.search("广州云宏信息科技股份有限公司");
    }
}
