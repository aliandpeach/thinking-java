/*
 * Created on 13-5-21
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2013 the original author or authors.
 */
package org.cneng.httpclient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 13-5-21
 */
public class Utils {
    public static byte[] toByteArray(File imageFile) throws Exception {
        BufferedImage img = ImageIO.read(imageFile);
        ByteArrayOutputStream buf = new ByteArrayOutputStream((int) imageFile.length());
        try {
            ImageIO.write(img, "jpg", buf);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return buf.toByteArray();
    }

    public static byte[] toByteArrayFromFile(String imageFile) throws Exception {
        InputStream is = null;

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            is = new FileInputStream(imageFile);
            byte[] b = new byte[1024];
            int n;
            while ((n = is.read(b)) != -1) {
                out.write(b, 0, n);
            }// end while

        } catch (Exception e) {
            throw new Exception("System error,SendTimingMms.getBytesFromFile", e);
        } finally {

            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }// end try
            }// end if

        }// end try
        return out.toByteArray();
    }
    //CRC32函数开始

    public static String doChecksum(String fileName) {

        try {

            CheckedInputStream cis = null;
            try {
                // Computer CRC32 checksum
                cis = new CheckedInputStream(
                        new FileInputStream(fileName), new CRC32());

            } catch (FileNotFoundException e) {
                //System.err.println("File not found.");
                //System.exit(1);
            }

            byte[] buf = new byte[128];
            while (cis.read(buf) >= 0) {
            }

            long checksum = cis.getChecksum().getValue();
            cis.close();
            //System.out.println( Integer.toHexString(new Long(checksum).intValue()));
            return Integer.toHexString(new Long(checksum).intValue());

        } catch (IOException e) {
            e.printStackTrace();
            //System.exit(1);
        }

        return null;

    }
    //CRC32函数结束


    //MD5校验函数开始

    /**
     * 获取指定文件的MD5值
     *
     * @param inputFile 文件的相对路径
     */
    public static String GetFileMD5(String inputFile) throws IOException {
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(inputFile);
            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
            byte[] buffer = new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0) ;
            messageDigest = digestInputStream.getMessageDigest();
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        } finally {
            try {
                digestInputStream.close();
            } catch (Exception e) {

            }
            try {
                fileInputStream.close();
            } catch (Exception e) {

            }
        }
    }

    public static String Md5(String s) throws IOException {
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            return byteArrayToHex(md);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

    //MD5校验函数结束

    public static String[] parseServers(String server) {
        String[] temps = server.split(",");
        List<String> list = new ArrayList<>();
        for (String s : temps) {
            String[] each = s.split(":");
            if (each.length < 3) {
                list.add(each[0]);
            } else {
                list.add(each[0] + ":" + each[1]);
            }
        }
        System.out.println(list);
        return list.toArray(new String[list.size()]);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.asList(parseServers("1000,service.taskok.com:9000:101,upload.taskok.com:9000:102,code.taskok.com:9000:103,bak.taskok.com:104")));
    }

}
