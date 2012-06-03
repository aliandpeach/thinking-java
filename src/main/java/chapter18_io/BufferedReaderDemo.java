package chapter18_io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-3
 * Time: 下午3:49
 * BufferedReader
 */
public class BufferedReaderDemo {

    /**
     * 利用BufferedReader读取文件内容
     * @param fileName
     * @return
     * @throws IOException
     */
    public String read(String fileName) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s=in.readLine()) != null) {
            sb.append(s).append("\n");
        }
        in.close();
        return sb.toString();
    }

    /**
     *
     */
    public void stringReader(String fileName) throws IOException {
        StringReader sr = new StringReader(read(fileName));
        int c;
        while ((c=sr.read()) != -1) {
            System.out.println((char) c);
        }
    }
}
