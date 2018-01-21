package examples.socket;

import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * SocketClient1
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2017/12/15
 */
public class SocketClient2 {
    public static void clientSocket() throws Exception {

        // 调试标志
        String TAG = "电力收费socket: ";
        // 客户端socket
        Socket clientSocket = null;
        // 服务器地址
        String ip = "222.56.37.6";
        // 端口号
        int port = 12823;
        // 缓冲区
        byte[] buffer = new byte[1029];
        final int BUFFER_LEN = buffer.length;
        // client输出流
        OutputStream clientOutputStream = null;

        try {
            // 建立socket
            System.out.println(TAG + "set up connection to the server ...");
            clientSocket = new Socket(ip, port);
            // 获取socket输出流(发送数据包)
            clientOutputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (clientOutputStream != null) {
            try {
                int dataIndex = 0;
                System.out.println(TAG + "begin to send request ...");
                // 功能码，BIN
                buffer[0] = 0x09;
                dataIndex++;

                // 操作员号-网点终端号 LC
                byte[] op = "11008888-1".getBytes();
                buffer[dataIndex] = (byte) op.length;
                dataIndex++;
                System.arraycopy(op, 0, buffer, dataIndex, op.length);
                dataIndex += op.length;

                // 终端号 LC
                byte[] ter = "1100008888".getBytes();
                buffer[dataIndex] = (byte) ter.length;
                dataIndex++;
                System.arraycopy(op, 0, buffer, dataIndex, ter.length);
                dataIndex += ter.length;

                // 终端类型 BIN
                buffer[dataIndex] = (byte) 40;
                dataIndex++;

                // 标识 BIN
                buffer[dataIndex] = (byte) 0;
                dataIndex++;

                // 参数区


                // 发送数据
                clientOutputStream.write(buffer, 0, BUFFER_LEN);
                System.out.println(TAG + "image has been sent ...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static int bytesToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    private static byte[] intToBytes(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) (num >> 24);
        result[1] = (byte) (num >> 16);
        result[2] = (byte) (num >> 8);
        result[3] = (byte) (num /*>> 0*/);
        return result;
    }

    public static String printBytes(byte[] bytes) {
        String result = Hex.encodeHexString(bytes);
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
        System.out.println(0x32);
    }
}
