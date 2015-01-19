package ch18io;//: io/BasicFileOutput.java

import java.io.*;

public class BasicFileOutput {
    private String outFileName = "BasicFileOutput.out";
    private String inString = "我是个好孩子！ ^_^";

    /**
     * 基本的文件写入
     *
     * @throws IOException
     */
    public void basicWriteFile() throws IOException {
        BufferedReader in = new BufferedReader(new StringReader(inString));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outFileName)));
        // PrintWriter out = new PrintWriter(outFileName); //这样写也行
        int lineCount = 1;
        String s;
        while ((s = in.readLine()) != null)
            out.println(lineCount++ + ": " + s);
        out.close();
        // Show the stored outFileName:
        System.out.println(new BufferedReaderDemo().read(outFileName));
    }
} /* (Execute to see output) */// :~
