package examples.socket.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import examples.socket.protocal.PowerDTO;
import examples.socket.protocal.WaterDTO;
import net.sf.json.JSONObject;

public class MessageService {
    //电力欠费
    public static void sendMessage(PowerDTO dto) {
        String host = Global.MESSAGE_HOST;
        String path = Global.MESSAGE_PATH;
        String method = Global.MESSAGE_METHOD;
        String appcode = Global.MESSAGE_APPCODE;
        String templateCode = Global.MESSAGE_TEMPLATECODE;
        String signName = Global.MESSAGE_SIGNNAME;
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> datas = new HashMap<String, String>();
        datas.put("name", dto.getPowerName());
        datas.put("no", dto.getPowerNo().substring(6));
        datas.put("fee1", dto.getPowerDetail_QE().toString());
        datas.put("fee2", dto.getPowerDetail_WYJ().toString());
        datas.put("fee3", dto.getPowerDetail_YJ().toString());
        datas.put("payfee", dto.getPowerSJJE().toString());
        JSONObject jobj = JSONObject.fromObject(datas);
        querys.put("ParamString", jobj.toString());
        querys.put("RecNum", dto.getUserPhone());
        querys.put("SignName", "李武");
        querys.put("TemplateCode", templateCode);
        System.out.println(jobj.toString());
        System.out.println(dto.getUserPhone());
        System.out.println(signName);
        System.out.println(templateCode);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //电力预存
    public static void sendMessage1(PowerDTO dto) {
        String host = Global.MESSAGE_HOST;
        String path = Global.MESSAGE_PATH;
        String method = Global.MESSAGE_METHOD;
        String appcode = Global.MESSAGE_APPCODE;
        String templateCode = Global.MESSAGE_TEMPLATECODE;
        String signName = Global.MESSAGE_SIGNNAME;
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> datas = new HashMap<String, String>();
        datas.put("name", dto.getPowerName());
        datas.put("no", dto.getPowerNo());
        datas.put("fee1", dto.getPowerDetail_QE().toString());
        datas.put("fee2", dto.getPowerDetail_WYJ().toString());
        datas.put("fee3", dto.getPowerDetail_YJ().toString());
        datas.put("payfee", dto.getPowerSJJE().toString());
        JSONObject jobj = JSONObject.fromObject(datas);
        querys.put("ParamString", jobj.toString());
        querys.put("RecNum", dto.getUserPhone());
        querys.put("SignName", signName);
        querys.put("TemplateCode", templateCode);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //水务欠费
    public static void sendMessage(WaterDTO dto) {
        String host = Global.MESSAGE_HOST;
        String path = Global.MESSAGE_PATH;
        String method = Global.MESSAGE_METHOD;
        String appcode = Global.MESSAGE_APPCODE;
        String templateCode = Global.MESSAGE_TEMPLATECODE;
        String signName = Global.MESSAGE_SIGNNAME;
        Map<String, String> headers = new HashMap<String, String>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> datas = new HashMap<String, String>();
        datas.put("name", dto.getWaterName());
        datas.put("no", dto.getWaterNo());
        datas.put("fee1", getFormatFee(dto.getWaterHJSF()));
        datas.put("fee2", getFormatFee(dto.getWaterHJWSF()));
        datas.put("fee3", getFormatFee(dto.getWaterFJFY()));
        datas.put("payfee", getFormatFee(dto.getWaterBCJF()));
        JSONObject jobj = JSONObject.fromObject(datas);
        querys.put("ParamString", jobj.toString());
        querys.put("RecNum", dto.getUserPhone());
        querys.put("SignName", "李武");
        querys.put("TemplateCode", templateCode);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getFormatFee(String s) {
        return Integer.toString(Integer.parseInt(s) / 100);
    }

    public static void main(String[] args) {
        String t = "0501001700166515";
        System.out.println(Integer.parseInt("000000004900") / 100);
    }
}
