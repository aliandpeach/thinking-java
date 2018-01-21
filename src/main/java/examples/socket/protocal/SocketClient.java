package examples.socket.protocal;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import examples.socket.service.Global;

public class SocketClient {

    private String host;
    private int port;

    public SocketClient() {
        this.host = Global.SERVER_IP;
        this.port = Global.SERVER_PORT;
    }

    public byte[] call(byte[] data) {
        byte[] result = null;
        int resultCode = 0;

        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<byte[]> future = new FutureTask<byte[]>(
                new Callable<byte[]>() {
                    public byte[] call() throws Exception {
                        return callService(data);
                    }

                    ;
                });

        executor.execute(future);
        try {
            result = (byte[]) future.get(180L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            resultCode = -1;
            future.cancel(true);
        } catch (ExecutionException e) {
            e.printStackTrace();
            resultCode = -1;
            future.cancel(true);
        } catch (TimeoutException e) {
            e.printStackTrace();
            resultCode = -1;
            future.cancel(true);
        } finally {
            executor.shutdown();
        }

        return result;
    }


    public byte[] callService(byte[] data) {
        byte[] ret = null;
        Socket client = null;
        OutputStream out = null;
        InputStream in = null;

        try {
            client = new Socket();
            client.setSendBufferSize(32768);
            client.setReceiveBufferSize(32768);
            client.setTcpNoDelay(true);
            InetSocketAddress endpoint = new InetSocketAddress(this.host, this.port);
            client.connect(endpoint);
            out = client.getOutputStream();
            out.write(data);
            out.flush();

            in = client.getInputStream();
            in.skip(5);
            byte[] tmp = new byte[2];
            in.read(tmp);
            short dataLen = ByteUtil.byte2Short4C(tmp);
            ret = new byte[dataLen];
            in.read(ret);
        } catch (ConnectException e) {
            e.printStackTrace();

        } catch (SocketException e) {
            e.printStackTrace();

        } catch (UnknownHostException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
                if (in != null) {
                    in.close();
                    in = null;
                }
                if (client != null) {
                    client.close();
                    client = null;
                }
            } catch (IOException e) {

            }
        }

        return ret;
    }


    public static void main(String args[]) {

    }
}