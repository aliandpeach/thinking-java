package examples.socket.service;

import java.util.HashMap;

public class Global {
    public static String SERVER_IP = "222.56.37.6";
    public static int SERVER_PORT = 12823;
    public static HashMap OPERATOR_NO;
    public static int TERMIAL_TYPE = 40;
    public static int TERMIAL_MARK = 0;
    public static int FUNCTION_LOGIN = 9;
    public static int FUNCTION_POWER_QUERY = 50;
    public static int FUNCTION_POWER_PAY = 51;
    public static int FUNCTION_WATER_QUERY = 55;
    public static int FUNCTION_WATER_PAY = 56;
    public static String DEFAULT_MAC = "00000000";
    public static int TIME_OUT = 5;
    public static byte[] MAC_CODE = new byte[8];//交易MAC码
    public static String MESSAGE_HOST = "http://sms.market.alicloudapi.com";
    public static String MESSAGE_PATH = "/singleSendSms";
    public static String MESSAGE_METHOD = "GET";
    public static String MESSAGE_APPCODE = "ca938a9e16554766a976f79ab3dcc657";
    public static String MESSAGE_TEMPLATECODE = "SMS_100290020";
    public static String MESSAGE_SIGNNAME = "李武";
    public static int PAY_TYPE = 1;
    public static int LISTEN_PORT = 19090;
}
