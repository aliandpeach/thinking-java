package org.cneng.httpclient;

/**
 *
 request.Headers.Add("SID",软件id);
 request.Headers.Add("HASH",md5(软件id+软件key.ToUpper()));   //32位MD5加密小写
 request.Headers.Add("UUVersion","1.0.0.1");
 ----没有登录之前，UserID就用100。登录成功后，服务器会返回UserID，之后的请求就用服务器返回的UserID
 request.Headers.Add("UID",UserID);
 ----没有登录之前，UserID就用100。登录成功后，服务器会返回UserID，之后的请求就用服务器返回的UserID
 request.Headers.Add("User-Agent", MD5(软件key.ToUpper() + UserID));
 ----用户登录时候的特殊header：
 ----除了以上header之外，增加如下：
 ----MAC把特殊符号去掉，纯粹字母数字
 request.Headers.Add("KEY",MD5(软件key.ToUpper()+UserName.ToUpper())+MAC);
 ----MAC把特殊符号去掉，纯粹字母数字
 request.Headers.Add("UUKEY", MD5(UserName.ToUpper() + MAC + 软件key.ToUpper()));
 ----查分时候的特殊header:
 request.Headers.Add("UUAgent", MD5(UserKEY.ToUpper() + UserID + 软件KEY));
 request.Headers.Add("KEY", UserKey);
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/2/2
 */

import java.io.File;
import java.io.IOException;

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
import static org.cneng.httpclient.ConfigUtil.get;

public class CheckCodeClient {
    private static final CheckCodeClient instance = new CheckCodeClient();
    private RequestModel model;

    private CheckCodeClient() {
        try {
            // 初始化配置文件和RequestModel
            ConfigUtil.getInstance();
            model = new RequestModel();
            model.setSwId(get("swId"));
            model.setSwKey(get("swKey"));
            model.setSwKeyUpper(get("swKeyUpper"));
            model.setMac(get("mac"));
            model.setUuVersion(get("uuVersion"));
            model.setUname(get("uname"));
            model.setPwmd5(Md5(get("pwmd5")));
            model.setFlushServer(get("flushServers"));
            //---------------------------------------------
            model.setFlushInternals(get("flushInternals"));
            model.setLoginServer(get("loginServer"));
            model.setUploadServer(get("uploadServer"));
            model.setResultServer(get("resultServer"));
            model.setBackupServer(get("backupServer"));
            //---------------------------------------------
            model.setUserId(get("userId"));
            model.setUserKey(get("userKey"));
            model.setTimeout(get("timeout"));
            model.setVersion(get("version"));
            model.setType(get("type"));

            // 第一步：刷新服务器列表
            String[] servers = getServers();
            model.setFlushInternals(servers[0]);
            model.setLoginServer(servers[1]);
            model.setUploadServer(servers[2]);
            model.setResultServer(servers[3]);
            if (servers.length > 4) {
                model.setBackupServer(servers[4]);
            }

            // 第二步：登录
            String[] userkeys = login();
            model.setUserId(userkeys[0]);
            model.setUserKey(userkeys[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CheckCodeClient getInstance() {
        return instance;
    }

    /**
     * 验证码识别整个流程
     * @param checkcodeFile 图片地址
     * @return 验证码
     */
    public static String checkCode(String checkcodeFile) {
        try {
            String uploadResult = instance.upload(checkcodeFile);
            if (uploadResult.contains("|")) {
                return uploadResult.split("|")[1];
            } else {
                return instance.getResult(uploadResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String[] getServers() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(model.getFlushServer());
            System.out.println("Executing request " + httpget.getRequestLine());
            // 所有请求的通用header：
            httpget.addHeader("SID", model.getSwId());
            httpget.addHeader("HASH", Md5(model.getSwId() + model.getSwKeyUpper()));
            httpget.addHeader("UUVersion", model.getUuVersion());
            httpget.addHeader("UID", model.getUserId());
            httpget.addHeader("User-Agent", Md5(model.getSwKeyUpper() + model.getUserId()));

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

    private String[] login() throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(
                    "http://" + model.getLoginServer() + "/Upload/UULogin.aspx?U="
                            + model.getUname() + "&p=" + model.getPwmd5());
            System.out.println("Executing request " + httpget.getRequestLine());

            // 所有请求的通用header：
            httpget.addHeader("SID", model.getSwId());
            httpget.addHeader("HASH", Md5(model.getSwId() + model.getSwKeyUpper()));
            httpget.addHeader("UUVersion", model.getUuVersion());
            httpget.addHeader("UID", model.getUserId());
            httpget.addHeader("User-Agent", Md5(model.getSwKeyUpper() + model.getUserId()));

            httpget.addHeader("KEY", Md5(model.getSwKeyUpper() + model.getUname().toUpperCase()) + model.getMac());
            httpget.addHeader("UUKEY", Md5(model.getUname().toUpperCase() + model.getMac() + model.getSwKeyUpper()));

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
            return new String[]{responseBody.split("_")[0], responseBody};
        } finally {
            httpclient.close();
        }
    }

    /**
     * 上传文件
     *
     * @return
     * @throws Exception
     */
    private String upload(String filename) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost("http://" + model.getUploadServer() + "/Upload/Processing.aspx");
            System.out.println("Executing request " + httppost.getRequestLine());

            // 所有请求的通用header：
            httppost.addHeader("SID", model.getSwId());
            httppost.addHeader("HASH", Md5(model.getSwId() + model.getSwKeyUpper()));
            httppost.addHeader("UUVersion", model.getUuVersion());
            httppost.addHeader("UID", model.getUserId());
            httppost.addHeader("User-Agent", Md5(model.getSwKeyUpper() + model.getUserId()));

            FileBody IMG = new FileBody(new File(filename));
            StringBody KEY = new StringBody(model.getUserKey().toUpperCase(), ContentType.TEXT_PLAIN);
            StringBody SID = new StringBody(model.getSwId(), ContentType.TEXT_PLAIN);
            StringBody SKEY = new StringBody(Md5(model.getUserKey().toLowerCase() +
                    model.getSwId() + model.getSwKey()), ContentType.TEXT_PLAIN);
            StringBody Version = new StringBody(model.getVersion(), ContentType.TEXT_PLAIN);
            StringBody TimeOut = new StringBody(model.getTimeout(), ContentType.TEXT_PLAIN);
            StringBody Type = new StringBody(model.getType(), ContentType.TEXT_PLAIN);
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
     *
     * @return
     * @throws Exception
     */
    private String getResult(String checkId) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet("http://" + model.getResultServer() +
                    "/Upload/GetResult.aspx?key=" + model.getUserKey() + "&ID=" + checkId);
            System.out.println("Executing request " + httpget.getRequestLine());

            // 所有请求的通用header：
            httpget.addHeader("SID", model.getSwId());
            httpget.addHeader("HASH", Md5(model.getSwId() + model.getSwKeyUpper()));
            httpget.addHeader("UUVersion", model.getUuVersion());
            httpget.addHeader("UID", model.getUserId());
            httpget.addHeader("User-Agent", Md5(model.getSwKeyUpper() + model.getUserId()));

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
            while ("-3".equals(responseBody)) {
                try {
                    System.out.println("----getResult sleep----");
                    Thread.sleep(Long.parseLong(model.getFlushInternals()));
                    responseBody = httpclient.execute(httpget, responseHandler);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return responseBody;
        } finally {
            httpclient.close();
        }
    }


    public static void main(String[] args) throws Exception {
        checkCode("D:/work/aa.png");
    }
}
