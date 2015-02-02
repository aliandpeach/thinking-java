package org.cneng.httpclient;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/2/2
 */

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import static org.cneng.httpclient.Utils.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class CheckCodeClient {

    public static String[] getServers() throws Exception {
        // 用户名
        String uname = "winhong";
        // 用户密码
        String pwmd5 = "vs_WinHong2014";
        // MAC地址
        String mac = "080027004CE8";
        // 软件id
        String swId = "103479";
        // UserID
        String userId = "100";
        // 软件key
        String swKey = "97dc993a6b614e03a35213c58b8c8f0e";
        String swKeyUpper = "97DC993A6B614E03A35213C58B8C8F0E";
        // UserKey
        String userKey = "";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("http://common.taskok.com:9000/Service/ServerConfig.aspx");
            System.out.println("Executing request " + httpget.getRequestLine());
            // 所有请求的通用header：
            httpget.addHeader("SID", swId);
            httpget.addHeader("HASH", Md5(swId + swKeyUpper));
            httpget.addHeader("UUVersion", "1.0.0.1");
            httpget.addHeader("UID", userId);
            httpget.addHeader("User-Agent", Md5(swKeyUpper + userId));

            // ----用户登录时候的特殊header：
//            httpget.addHeader("KEY", parseStrToMd5L32(swKeyUpper + uname.toUpperCase()) + mac);
//            httpget.addHeader("UUKEY", parseStrToMd5L32(uname.toUpperCase() + mac + swKeyUpper));

            // ----查分时候的特殊header：
//            httpget.addHeader("UUAgent", parseStrToMd5L32(userKey.toUpperCase() + userId + swKey));
//            httpget.addHeader("KEY", userKey);


            // request.Headers.Add("SID",软件id);
            // request.Headers.Add("HASH",md5(软件id+软件key.ToUpper()));   //32位MD5加密小写
            // request.Headers.Add("UUVersion","1.0.0.1");
            // ----没有登录之前，UserID就用100。登录成功后，服务器会返回UserID，之后的请求就用服务器返回的UserID
            // request.Headers.Add("UID",UserID);
            // ----没有登录之前，UserID就用100。登录成功后，服务器会返回UserID，之后的请求就用服务器返回的UserID
            // request.Headers.Add("User-Agent", MD5(软件key.ToUpper() + UserID));
            // ----用户登录时候的特殊header：
            // ----除了以上header之外，增加如下：
            // ----MAC把特殊符号去掉，纯粹字母数字
            // request.Headers.Add("KEY",MD5(软件key.ToUpper()+UserName.ToUpper())+MAC);
            // ----MAC把特殊符号去掉，纯粹字母数字
            // request.Headers.Add("UUKEY", MD5(UserName.ToUpper() + MAC + 软件key.ToUpper()));
            // ----查分时候的特殊header:
            // request.Headers.Add("UUAgent", MD5(UserKEY.ToUpper() + UserID + 软件KEY));
            // request.Headers.Add("KEY", UserKey);

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
            String responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return parseServers(responseBody);
        } finally {
            httpclient.close();
        }
    }

    public static String login() throws Exception {
        // 用户名
        String uname = "winhong";
        // 用户密码
        String pwmd5 = "vs_WinHong2014";
        // MAC地址
        String mac = "080027004CE8";
        // 软件id
        String swId = "103479";
        // UserID
        String userId = "100";
        // 软件key
        String swKey = "97dc993a6b614e03a35213c58b8c8f0e";
        String swKeyUpper = "97DC993A6B614E03A35213C58B8C8F0E";

        // 登录服务器地址
        String loginServer = "login.uudama.com:9000";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(
                    "http://" + loginServer + "/Upload/UULogin.aspx?U=" + uname + "&p=" + Md5(pwmd5));
            System.out.println("Executing request " + httpget.getRequestLine());
            // 所有请求的通用header：
            httpget.addHeader("SID", swId);
            httpget.addHeader("HASH", Md5(swId + swKeyUpper));
            httpget.addHeader("UUVersion", "1.0.0.1");
            httpget.addHeader("UID", userId);
            httpget.addHeader("User-Agent", Md5(swKeyUpper + userId));
            httpget.addHeader("KEY", Md5(swKeyUpper + uname.toUpperCase()) + mac);
            httpget.addHeader("UUKEY", Md5(uname.toUpperCase() + mac + swKeyUpper));

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
            String responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody.split("_")[0];
        } finally {
            httpclient.close();
        }
    }

    /**
     * 上传文件
     * @return
     * @throws Exception
     */
    public static String upload() throws Exception {
        // 用户名
        String uname = "winhong";
        // 用户密码
        String pwmd5 = "vs_WinHong2014";
        // MAC地址
        String mac = "080027004CE8";
        // 软件id
        String swId = "103479";
        // 软件key
        String swKey = "97dc993a6b614e03a35213c58b8c8f0e";
        String swKeyUpper = "97DC993A6B614E03A35213C58B8C8F0E";

        // 登录服务器地址
        String uploadServer = "upload.uuwise.com:9000";
        // UserId
        String userId = "606982";
        // UserKey
        String userKey = "606982_WINHONG_B2-6E-69-B1-49-F0-81-91-6C-11-C0-6C-DB-FF-4A-" +
                "F4_B1-8A-80-58-3B-1C-04-2B-05-37-7A-B5-AE-1F-6D-3E-39-C5-F8-34";
        String filename = "D:\\work\\verify.png";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("http://" + uploadServer + "/Upload/Processing.aspx");
            System.out.println("Executing request " + httppost.getRequestLine());
            // 所有请求的通用header：
            httppost.addHeader("SID", swId);
            httppost.addHeader("HASH", Md5(swId + swKeyUpper));
            httppost.addHeader("UUVersion", "1.0.0.1");
            httppost.addHeader("UID", userId);
            httppost.addHeader("User-Agent", Md5(swKeyUpper + userId));

            FileBody IMG = new FileBody(new File(filename));
            StringBody KEY = new StringBody(userKey.toUpperCase(), ContentType.TEXT_PLAIN);
            StringBody SID = new StringBody(swId, ContentType.TEXT_PLAIN);
            StringBody SKEY = new StringBody(Md5(userKey.toLowerCase()+swId+swKey), ContentType.TEXT_PLAIN);
            StringBody Version = new StringBody("100", ContentType.TEXT_PLAIN);
            StringBody TimeOut = new StringBody("60000", ContentType.TEXT_PLAIN);
            StringBody Type = new StringBody("1104", ContentType.TEXT_PLAIN);
            StringBody GUID = new StringBody(GetFileMD5(filename), ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("IMG", IMG)
                    .addPart("KEY", KEY)
                    .addPart("SID", SID)
                    .addPart("SKEY", SKEY)
                    .addPart("Version", Version)
                    .addPart("TimeOut", TimeOut)
                    .addPart("Type", Type)
                    .addPart("GUID", GUID)
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

    /**
     * 轮询结果
     * @return
     * @throws Exception
     */
    public static String getResult() throws Exception {
        // 用户名
        String uname = "winhong";
        // 用户密码
        String pwmd5 = "vs_WinHong2014";
        // MAC地址
        String mac = "080027004CE8";
        // 软件id
        String swId = "103479";
        // UserID
        String userId = "100";
        // 软件key
        String swKey = "97dc993a6b614e03a35213c58b8c8f0e";
        String swKeyUpper = "97DC993A6B614E03A35213C58B8C8F0E";

        // 登录服务器地址
        String resultServer = "upload.uuwise.com:9000";
        // UserKey
        String userKey = "606982_WINHONG_B2-6E-69-B1-49-F0-81-91-6C-11-C0-6C-DB-FF-4A-" +
                "F4_B1-8A-80-58-3B-1C-04-2B-05-37-7A-B5-AE-1F-6D-3E-39-C5-F8-34";
        // 验证码ID
        String checkId = "223498048";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(
                    "http://" + resultServer + "/Upload/GetResult.aspx?key=" + userKey + "&ID=" + checkId);
            System.out.println("Executing request " + httpget.getRequestLine());
            // 所有请求的通用header：
            httpget.addHeader("SID", swId);
            httpget.addHeader("HASH", Md5(swId + swKeyUpper));
            httpget.addHeader("UUVersion", "1.0.0.1");
            httpget.addHeader("UID", userId);
            httpget.addHeader("User-Agent", Md5(swKeyUpper + userId));

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
            String responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;
        } finally {
            httpclient.close();
        }
    }



    public static void main(String[] args) throws Exception {
//        String[] servers = getServers();
//        String userId = login();
//        String checkId = upload();  // 223498048
        getResult();

    }
}
