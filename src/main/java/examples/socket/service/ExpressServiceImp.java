package examples.socket.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.thrift.TException;

import examples.socket.protocal.ByteUtil;
import examples.socket.protocal.PowerDTO;
import examples.socket.protocal.PowerPayProtocal;
import examples.socket.protocal.PowerQueryProtocal;
import examples.socket.protocal.Protocal;
import examples.socket.protocal.SocketClient;
import examples.socket.protocal.WaterDTO;
import examples.socket.protocal.WaterPayProtocal;
import examples.socket.protocal.WaterQueryProtocal;
import net.sf.json.JSONObject;

public class ExpressServiceImp implements ExpressService.Iface {

    @Override
    public String powerQuery(String json) throws TException {
        JSONObject ret = null;
        JSONObject jobj = null;
        PowerDTO dto = null;
        try {
            System.out.println(json);
            jobj = JSONObject.fromObject(json);
            PowerQueryProtocal query = new PowerQueryProtocal(jobj);
            Protocal protocal = new Protocal();
            query.encode();
            protocal.build(query.getOutstream().toByteArray());

            SocketClient client = new SocketClient();
            byte[] tmp = client.call(protocal.getOutstream().toByteArray());
            int result = tmp[1] & 0xFF;
            if (result == 0) {
                dto = query.decode(tmp);
                System.out.println(ByteUtil.toHexString0x(tmp));
            } else {
                dto = new PowerDTO();
                dto.setResult_code(160);
                int len = tmp[3];
                byte[] bytes = new byte[len];
                System.arraycopy(tmp, 4, bytes, 0, len);
                dto.setMessage(new String(bytes));
            }
            ret = JSONObject.fromObject(dto);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<String, Object>();
            Result res = new Result();
            res.setMessage(e.getMessage());
            res.setResult_code(-1);
            ret = JSONObject.fromObject(map);
        }
        return ret.toString();
    }

    @Override
    public String powerPay(String json) throws TException {
        JSONObject ret = null;
        PowerDTO dto = null;
        JSONObject jobj = null;

        try {

            jobj = JSONObject.fromObject(json);
            PowerPayProtocal pay = new PowerPayProtocal(jobj);
            Protocal protocal = new Protocal();
            pay.encode();
            protocal.build(pay.getOutstream().toByteArray());
            SocketClient client = new SocketClient();
            byte[] tmp = client.call(protocal.getOutstream().toByteArray());
            int result = tmp[1] & 0xFF;
            if (result == 0) {
                dto = pay.decode(tmp);
                dto.setResult_code(0);
                dto.setPowerSJJE(jobj.getString("je"));
                dto.setUserPhone(jobj.getString("userPhone"));
                dto.setType(jobj.getString("type"));
                dto.setMessage("缴费成功！户号:" + dto.getPowerNo() + "本次缴费:" + jobj.getString("je") + "元");
                //发送短信凭证
                MessageThread t = new MessageThread(dto);
                t.start();

            } else {
                dto = new PowerDTO();
                dto.setResult_code(160);
                int len = tmp[3];
                byte[] bytes = new byte[len];
                System.arraycopy(tmp, 4, bytes, 0, len);
                dto.setMessage(new String(bytes));

            }
            ret = JSONObject.fromObject(dto);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<String, Object>();
            Result res = new Result();
            res.setMessage(e.getMessage());
            res.setResult_code(-1);
            ret = JSONObject.fromObject(map);
        }
        return ret.toString();
    }

    @Override
    public String waterQuery(String json) throws TException {
        JSONObject ret = null;
        JSONObject jobj = null;
        WaterDTO dto = null;
        try {
            jobj = JSONObject.fromObject(json);
            WaterQueryProtocal query = new WaterQueryProtocal(jobj);
            Protocal protocal = new Protocal();
            query.encode();
            protocal.build(query.getOutstream().toByteArray());
            SocketClient client = new SocketClient();
            byte[] tmp = client.call(protocal.getOutstream().toByteArray());
            int result = tmp[1] & 0xFF;
            if (result == 0) {
                dto = query.decode(tmp);
            } else {
                dto = new WaterDTO();
                dto.setResult_code(160);
                int len = tmp[3];
                byte[] bytes = new byte[len];
                System.arraycopy(tmp, 4, bytes, 0, len);
                dto.setMessage(new String(bytes));

            }
            ret = JSONObject.fromObject(dto);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<String, Object>();
            Result res = new Result();
            res.setMessage(e.getMessage());
            res.setResult_code(-1);
            ret = JSONObject.fromObject(map);
        }
        return ret.toString();
    }

    @Override
    public String waterPay(String json) throws TException {
        JSONObject ret = null;
        WaterDTO dto = null;
        JSONObject jobj = null;

        try {
            jobj = JSONObject.fromObject(json);
            WaterPayProtocal pay = new WaterPayProtocal(jobj);
            Protocal protocal = new Protocal();
            pay.encode();
            protocal.build(pay.getOutstream().toByteArray());
            SocketClient client = new SocketClient();
            byte[] tmp = client.call(protocal.getOutstream().toByteArray());
            int result = tmp[1] & 0xFF;
            if (result == 0) {
                dto = pay.decode(tmp);
                dto.setResult_code(0);
                dto.setWaterSJJE(jobj.getString("je"));
                dto.setUserPhone(jobj.getString("userPhone"));
                dto.setType(jobj.getString("type"));
                dto.setMessage("缴费成功！户号:" + dto.getWaterNo() + "本次缴费:" + jobj.getString("je") + "元");
                //发送短信凭证
                MessageThread t = new MessageThread(dto);
                t.start();

            } else {
                dto = new WaterDTO();
                dto.setResult_code(160);
                int len = tmp[3];
                byte[] bytes = new byte[len];
                System.arraycopy(tmp, 4, bytes, 0, len);
                dto.setMessage(new String(bytes));

            }
            ret = JSONObject.fromObject(dto);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<String, Object>();
            Result res = new Result();
            res.setMessage(e.getMessage());
            res.setResult_code(-1);
            ret = JSONObject.fromObject(map);
        }
        return ret.toString();
    }

    public static void main(String[] args) {
        ExpressServiceImp imp = new ExpressServiceImp();
        try {
            imp.powerQuery("1700764380");
        } catch (Exception ex) {

        }
    }

}
