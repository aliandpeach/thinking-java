package examples.socket.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import examples.socket.protocal.*;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

import net.sf.json.JSONObject;


public class Server {
    public static void loadConfig() {
        System.out.println("加载配置文件开始.....");
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile("./config.properties", "r");
            String line = "";
            Map<String, String> map = new HashMap<String, String>();
            while ((line = file.readLine()) != null) {
                String[] sub = line.split("=");
                map.put(sub[0], sub[1]);
            }
            HashMap optsMap = loadOperator(map.get("OPERATOR_NO"));

            Global.OPERATOR_NO = optsMap;
            Global.DEFAULT_MAC = map.get("DEFAULT_MAC");
            Global.SERVER_IP = map.get("SERVER_IP");
            Global.SERVER_PORT = Integer.parseInt(map.get("SERVER_PORT"));
            Global.TERMIAL_MARK = Integer.parseInt(map.get("TERMIAL_MARK"));
            Global.TERMIAL_TYPE = Integer.parseInt(map.get("TERMIAL_TYPE"));
            Global.FUNCTION_LOGIN = Integer.parseInt(map.get("FUNCTION_LOGIN"));
            Global.FUNCTION_POWER_QUERY = Integer.parseInt(map.get("FUNCTION_POWER_QUERY"));
            Global.FUNCTION_POWER_QUERY = Integer.parseInt(map.get("FUNCTION_POWER_QUERY"));
            Global.FUNCTION_WATER_QUERY = Integer.parseInt(map.get("FUNCTION_WATER_QUERY"));
            Global.FUNCTION_WATER_PAY = Integer.parseInt(map.get("FUNCTION_WATER_PAY"));
            Global.TIME_OUT = Integer.parseInt(map.get("TIME_OUT"));
            Global.MESSAGE_HOST = map.get("MESSAGE_HOST");
            Global.MESSAGE_PATH = map.get("MESSAGE_PATH");
            Global.MESSAGE_METHOD = map.get("MESSAGE_METHOD");
            Global.MESSAGE_APPCODE = map.get("MESSAGE_APPCODE");
            Global.MESSAGE_TEMPLATECODE = map.get("MESSAGE_TEMPLATECODE");
            Global.MESSAGE_SIGNNAME = map.get("MESSAGE_SIGNNAME");
            Global.PAY_TYPE = Integer.parseInt(map.get("PAY_TYPE"));
            Global.LISTEN_PORT = Integer.parseInt(map.get("LISTEN_PORT"));
            System.out.println("加载配置文件结束");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("读取配置文件错误，系统退出");
            System.exit(0);
        }
    }

    public static void login() {
        Set set = Global.OPERATOR_NO.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            Operator opt = (Operator) Global.OPERATOR_NO.get(key);
            LoginProtocal login = new LoginProtocal(opt);
            Protocal protocal = new Protocal();
            login.encode();
            protocal.build(login.getOutstream().toByteArray());
            SocketClient client = new SocketClient();
            try {
                System.out.println("操作员登录操作开始");
                System.out.println(ByteUtil.toHexString0x(protocal.getOutstream().toByteArray()));
                byte[] ret = client.call(protocal.getOutstream().toByteArray());
                System.out.println(ByteUtil.toHexString0x(ret));
                System.out.println("操作员登录成功");
                byte[] tmp = new byte[8];
                System.arraycopy(ret, 3, tmp, 0, 8);
                opt.setMacCode(tmp);
                Global.OPERATOR_NO.put(key, opt);
                Thread.currentThread().sleep(5000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void login2() {
        Operator opt = new Operator();
        opt.setAgentID("20001");
        opt.setOperatorID("11008888-1");
        opt.setTermialNo("1100008888");
        opt.setPassword("11111111");
//        opt.setMacCode();
        LoginProtocal login = new LoginProtocal(opt);
        login.encode();

        Protocal protocal = new Protocal();
        protocal.build(login.getOutstream().toByteArray());
        SocketClient client = new SocketClient();
        try {
            System.out.println("操作员登录操作开始");
            System.out.println(ByteUtil.printBytes(protocal.getOutstream().toByteArray()));
            byte[] ret = client.call(protocal.getOutstream().toByteArray());
            System.out.println("操作员登录成功");
            System.out.println(ByteUtil.printBytes(ret));
            byte[] tmp = new byte[8];
            System.arraycopy(ret, 3, tmp, 0, 8);
            opt.setMacCode(tmp);
            System.out.println("MacCode=" + ByteUtil.printBytes(tmp));
            Thread.currentThread().sleep(5000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void powerQuery() {
        PowerDTO dto;
        Operator opt = new Operator();
        opt.setAgentID("20001");
        opt.setOperatorID("11008888-1");
        opt.setTermialNo("1100008888");
        opt.setMacCode(ByteUtil.hexStringToByteArray("de8d55142b18deb7"));
        PowerQueryProtocal query = new PowerQueryProtocal("1700764380", opt);
        query.encode();
        Protocal protocal = new Protocal();
        protocal.build(query.getOutstream().toByteArray());
        System.out.println("查询电费开始。。。");
        SocketClient client = new SocketClient();
        byte[] tmp = client.call(protocal.getOutstream().toByteArray());
        int result = tmp[1] & 0xFF;
        if (result == 0) {
            dto = query.decode(tmp);
            System.out.println(ByteUtil.printBytes(tmp));
            System.out.println("查询电费成功。。。流水号=" + dto.getYwlsh() + ",月份=" + dto.getPowerMoth());
        } else {
            dto = new PowerDTO();
            dto.setResult_code(160);
            int len = tmp[3];
            byte[] bytes = new byte[len];
            System.arraycopy(tmp, 4, bytes, 0, len);
            dto.setMessage(new String(bytes));
            System.out.println("查询电费失败。。。" + dto.getMessage());
        }
    }

    public static void powerPay() {
        PowerDTO dto;
        Operator opt = new Operator();
        opt.setAgentID("20001");
        opt.setOperatorID("11008888-1");
        opt.setTermialNo("1100008888");
        opt.setMacCode(ByteUtil.hexStringToByteArray("de8d55142b18deb7"));

        PowerPayParam payParam = new PowerPayParam();
        payParam.setBusinessNo("1016344300");
        payParam.setPaymentAmount("0");
        payParam.setYearMonth("201702");

        PowerPayProtocal pay = new PowerPayProtocal(opt, payParam);
        pay.encode();
        Protocal protocal = new Protocal();
        protocal.build(pay.getOutstream().toByteArray());
        System.out.println("缴纳电费开始，业务流水号=" + payParam.getBusinessNo());

        SocketClient client = new SocketClient();
        byte[] tmp = client.call(protocal.getOutstream().toByteArray());
        int result = tmp[1] & 0xFF;
        if (result == 0) {
            dto = pay.decode(tmp);
            dto.setResult_code(0);
            String msg = "缴费成功！户号:" + dto.getPowerNo() + "本次缴费:" + payParam.getPaymentAmount() + "元";
            System.out.println(msg);
            dto.setMessage(msg);
        } else {
            dto = new PowerDTO();
            dto.setResult_code(160);
            int len = tmp[3];
            byte[] bytes = new byte[len];
            System.arraycopy(tmp, 4, bytes, 0, len);
            dto.setMessage(new String(bytes));
            System.out.println("缴费失败，msg = " + new String(bytes, Charset.forName("GBK")));
        }
    }

    public static HashMap loadOperator(String input) {
        String[] opts = input.split("\\|");
        HashMap map = new HashMap();
        for (int i = 0; i < opts.length; i++) {
            String[] opt = opts[i].split(",");
            Operator operator = new Operator();
            operator.setAgentID(opt[0]);
            operator.setOperatorID(opt[1]);
            operator.setTermialNo(opt[2]);
            operator.setPassword(opt[3]);
            map.put(opt[0], operator);
        }
        return map;
    }

    public static void main(String[] as) {
        //加载配置文件
//        loadConfig();
        //处理登录，多代理商逻辑需更改
        powerQuery();
        powerPay();
        TNonblockingServerTransport serverTransport = null;

        try {

            serverTransport = new TNonblockingServerSocket(Global.LISTEN_PORT);

        } catch (TTransportException e) {

            e.printStackTrace();

        }
        System.out.println("服务启动开始....");
        // Server 为Hello接口的实现类

        ExpressService.Processor<ExpressService.Iface> processor = new ExpressService.Processor<ExpressService.Iface>(new ExpressServiceImp());
        Factory protFactory = new Factory(true, true);

        TNonblockingServer.Args args = new TNonblockingServer.Args(serverTransport);

        args.processor(processor);

        args.protocolFactory(protFactory);

        TServer server = new TNonblockingServer(args);

        System.out.println("服务启动成功，监听端口:" + Global.LISTEN_PORT);

        server.serve();

    }

}
