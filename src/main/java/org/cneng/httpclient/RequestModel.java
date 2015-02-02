package org.cneng.httpclient;

/**
 * 请求的模型
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/2/2
 */
public class RequestModel {
    // 用户名
    private String uname = "winhong";
    // 用户密码
    private String pwmd5 = "vs_WinHong2014";
    // MAC地址
    private String mac = "080027004CE8";
    // 软件id
    private String swId = "103479";
    // UserID
    private String userId = "100";
    // UserKey
    private String userKey = "606982_WINHONG_B2-6E-69-B1-49-F0-81-91-6C-11-C0-6C-DB-FF-4A-" +
            "F4_B1-8A-80-58-3B-1C-04-2B-05-37-7A-B5-AE-1F-6D-3E-39-C5-F8-34";
    // 软件key
    private String swKey = "97dc993a6b614e03a35213c58b8c8f0e";
    // 软件key大写
    private String swKeyUpper = "97DC993A6B614E03A35213C58B8C8F0E";
    // 登录服务器地址
    private String loginServer = "login.uudama.com:9000";
    // 上传服务器地址
    private String uploadServer = "upload.uuwise.com:9000";
    // 获取结果服务器地址
    private String resultServer = "upload.uuwise.com:9000";
    // 备份服务器地址
    private String backupServer = "upload.uuwise.com:9000";
    // 验证码图片路径
    private String picPath = "";
    // 验证码ID
    private String checkId = "223498048";

    /**
     * 获取 uname.
     *
     * @return uname.
     */
    public String getUname() {
        return uname;
    }

    /**
     * 获取 swKeyUpper.
     *
     * @return swKeyUpper.
     */
    public String getSwKeyUpper() {
        return swKeyUpper;
    }

    /**
     * 设置 loginServer.
     *
     * @param loginServer loginServer.
     */
    public void setLoginServer(String loginServer) {
        this.loginServer = loginServer;
    }

    /**
     * 设置 mac.
     *
     * @param mac mac.
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * 设置 uname.
     *
     * @param uname uname.
     */
    public void setUname(String uname) {
        this.uname = uname;
    }

    /**
     * 设置 userId.
     *
     * @param userId userId.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取 userId.
     *
     * @return userId.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 获取 swId.
     *
     * @return swId.
     */
    public String getSwId() {
        return swId;
    }

    /**
     * 设置 pwmd5.
     *
     * @param pwmd5 pwmd5.
     */
    public void setPwmd5(String pwmd5) {
        this.pwmd5 = pwmd5;
    }

    /**
     * 设置 swKeyUpper.
     *
     * @param swKeyUpper swKeyUpper.
     */
    public void setSwKeyUpper(String swKeyUpper) {
        this.swKeyUpper = swKeyUpper;
    }

    /**
     * 设置 swId.
     *
     * @param swId swId.
     */
    public void setSwId(String swId) {
        this.swId = swId;
    }

    /**
     * 获取 pwmd5.
     *
     * @return pwmd5.
     */
    public String getPwmd5() {
        return pwmd5;
    }

    /**
     * 获取 loginServer.
     *
     * @return loginServer.
     */
    public String getLoginServer() {
        return loginServer;
    }

    /**
     * 设置 swKey.
     *
     * @param swKey swKey.
     */
    public void setSwKey(String swKey) {
        this.swKey = swKey;
    }

    /**
     * 获取 mac.
     *
     * @return mac.
     */
    public String getMac() {
        return mac;
    }

    /**
     * 获取 swKey.
     *
     * @return swKey.
     */
    public String getSwKey() {
        return swKey;
    }

    /**
     * 获取 picPath.
     *
     * @return picPath.
     */
    public String getPicPath() {
        return picPath;
    }

    /**
     * 设置 picPath.
     *
     * @param picPath picPath.
     */
    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    /**
     * 设置 backupServer.
     *
     * @param backupServer backupServer.
     */
    public void setBackupServer(String backupServer) {
        this.backupServer = backupServer;
    }

    /**
     * 获取 uploadServer.
     *
     * @return uploadServer.
     */
    public String getUploadServer() {
        return uploadServer;
    }

    /**
     * 设置 uploadServer.
     *
     * @param uploadServer uploadServer.
     */
    public void setUploadServer(String uploadServer) {
        this.uploadServer = uploadServer;
    }

    /**
     * 获取 resultServer.
     *
     * @return resultServer.
     */
    public String getResultServer() {
        return resultServer;
    }

    /**
     * 获取 backupServer.
     *
     * @return backupServer.
     */
    public String getBackupServer() {
        return backupServer;
    }

    /**
     * 设置 resultServer.
     *
     * @param resultServer resultServer.
     */
    public void setResultServer(String resultServer) {
        this.resultServer = resultServer;
    }

    /**
     * 获取 userKey.
     *
     * @return userKey.
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * 设置 userKey.
     *
     * @param userKey userKey.
     */
    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    /**
     * 获取 checkId.
     *
     * @return checkId.
     */
    public String getCheckId() {
        return checkId;
    }

    /**
     * 设置 checkId.
     *
     * @param checkId checkId.
     */
    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }
}
