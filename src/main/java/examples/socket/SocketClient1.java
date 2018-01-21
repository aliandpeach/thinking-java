package examples.socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * SocketClient1
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2017/12/15
 */
public class SocketClient1 {
    public static void clientSocket() throws Exception {

        // 调试标志
        String TAG = "ClientSocket: ";
        // 客户端socket
        Socket clientSocket = null;
        // 服务器地址
        String ip = "127.0.0.1";
        // 端口号
        int port = 9998;
        // 缓冲区
        byte[] buffer = new byte[1029];
        final int BUFFER_LEN = buffer.length;
        byte[] fullData = {0, 1, 0, 2, 4};
        // 实际长度
        int realLen = 0;
        // 文件末尾标志
        int cf = 0;
        // 文件输入流
        FileInputStream fileInputStream = null;
        // client输出流
        OutputStream clientOutputStream = null;
        // 文件路径
        File file = new File("res/image1.jpg");

        try {
            // 建立socket
            System.out.println(TAG + "set up connection to the server ...");
            clientSocket = new Socket(ip, port);
            // 获取文件输入流(读取要发送的图片)
            fileInputStream = new FileInputStream(file);
            // 获取socket输出流(发送数据包)
            clientOutputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (clientOutputStream != null) {
            try {
                System.out.println(TAG + "begin to send the image ...");
                while (cf != -1) {
                    // 读取数据放到数据区域
                    cf = fileInputStream.read(buffer, 5, BUFFER_LEN - 5);
                    if (cf == -1) {
                        // 将长度转为byte[]
                        byte[] by = changeInt2Byte(realLen);
                        // 将长度数据整合到数据包里面
                        System.arraycopy(by, 0, buffer, 0, by.length);
                        // 标志位置2
                        buffer[0] = 2;
                        // 发送数据
                        clientOutputStream.write(buffer, 0, realLen + 5);
                    } else {
                        // 缓存实际的数据长度
                        realLen = cf;
                        System.arraycopy(fullData, 0, buffer, 0, fullData.length);
                        // 标志位置1
                        buffer[0] = 1;
                        // 发送数据
                        clientOutputStream.write(buffer, 0, BUFFER_LEN);
                    }
                }
                System.out.println(TAG + "image has been sent ...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int changeByte2Int(byte[] bytes) {
        int result = 0;
        int[] a = {1000, 100, 10, 1};
        for (int i = 0, j = 0; i < bytes.length; i++, j++) {
            int k = (int) bytes[i];
            result += k * a[j];
        }
        return result;
    }

    public static byte[] changeInt2Byte(int len) {
        byte[] bytes = new byte[5];
        byte[] bytesReturn = new byte[5];
        int result = 0;
        int i;
        int j = 1;
        int z = 4;
        int k = 1;
        for (i = 4; i >= 0; i--) {
            result = len % 10;
            bytes[i] = (byte) result;
            len /= 10;
        }

        for (; j < bytes.length; j++) {
            bytesReturn[k] = bytes[j];
            k++;
        }
        return bytesReturn;
    }

    public static void printBytes(byte[] bytes) {
        int len = bytes.length;
        for (byte b : bytes) {
            System.out.print(b + " ");
        }
    }
}
