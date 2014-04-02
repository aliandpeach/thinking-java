package ch18io;

import java.io.*;

/**
 * 演示基本的文件读取和写入
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2014/4/2
 */
public class FileReaderWriter {
    public void init() {
        System.out.println("-----AutoStartServlet start-----");
        //ip="127.0.0.1" port="1433" dbname="acc_sys" user="sa" pwd="c"
        String path = "/home/yidao/";
        String xmlpath = path + File.separator + "product.xml";
        String newline = System.getProperty("line.separator");
        String envJson = System.getenv("VCAP_SERVICES");
        String ip = "127.0.0.1";
        String port = "8080";
        String db_name = "mango15";
        String username = "postgres";
        String password = "123456";
        System.out.println(ip);
        System.out.println(port);
        System.out.println(db_name);
        System.out.println(username);
        System.out.println(password);
        BufferedReader s = null;
        Writer out = null;
        try {
            s = new BufferedReader(new InputStreamReader(new FileInputStream(xmlpath), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String tmp = s.readLine();
            while (tmp != null ) {
                sb.append(tmp).append(newline);
                tmp = s.readLine();
            }
            String all = sb.toString();
            all = all.replaceFirst("ip=\".*?\"", "ip=\"" + ip + "\"");
            all = all.replaceFirst("port=\".*?\"", "port=\"" + port + "\"");
            all = all.replaceFirst("dbname=\".*?\"", "dbname=\"" + db_name + "\"");
            all = all.replaceFirst("user=\".*?\"", "user=\"" + username + "\"");
            all = all.replaceFirst("pwd=\".*?\"", "pwd=\"" + password + "\"");
            out = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(xmlpath), "UTF-8"));
            out.append(all);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(s != null) s.close();
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("-----AutoStartServlet end-----");
    }
}
