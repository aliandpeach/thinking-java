package ch18io;

import java.io.*;

/**
 * 字节流缓冲读写演示
 */
public class FileCopyBufferedStream {
    public static void main(String[] args) {
//        byteCopyFile();
//        byte[] bb = fileToBytes("D:/0001.jpg");
//        bytesToFile(bb, "D:/0003.jpg");
    }

    private static void byteCopyFile() {
        String inFileStr = "D:/0001.jpg";
        String outFileStr = "D:/0002.jpg";
        long startTime, elapsedTime;  // for speed benchmarking

        // Check file length
        File fileIn = new File(inFileStr);
        System.out.println("File size is " + fileIn.length() + " bytes");
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(inFileStr));
            out = new BufferedOutputStream(new FileOutputStream(outFileStr));
            startTime = System.nanoTime();
            int byteRead;
            while ((byteRead = in.read()) != -1) {
                out.write(byteRead);
            }
            elapsedTime = System.nanoTime() - startTime;
            System.out.println("Elapsed Time is " + (elapsedTime / 1000000.0) + " msec");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static byte[] fileToBytes(String inFileStr) {
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(inFileStr));
            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int n;
            while (-1 != ( n = in.read(buffer))) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void bytesToFile(byte[] bs, String outFileStr) {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(outFileStr));
            out.write(bs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
