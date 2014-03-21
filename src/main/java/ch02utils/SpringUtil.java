/*
 * Created on 12-11-20
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
 * Copyright @2012 the original author or authors.
 */
package ch02utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.lang.ArrayUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 20个有用的代码片段
 *
 * @author XiongNeng
 * @version 1.0
 * @since 12-11-20
 */
public class SpringUtil {
    /**
     * 字符串有整型的相互转换
     */
    public void stringAndInt() {
        String a = String.valueOf(2);   //integer to numeric string
        int i = Integer.parseInt(a); //numeric string to an int
    }

    /**
     * 向文件末尾添加内容
     *
     * @throws Exception
     */
    public void appendToFile() throws Exception {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("filename", true));
            out.write("aString");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 得到当前方法的名字
     *
     * @return 当前方法的名字
     */
    public String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }

    /**
     * 转字符串到日期
     *
     * @param dateStr 字符串
     * @return 日期
     * @throws ParseException
     */
    public Date stringToDate(String dateStr) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
    }

    /**
     * 把 Java util.Date 与 sql.Date 相互转化
     */
    public void sqlAndUtilDate() {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println(sqlDate);
    }

    /**
     * 使用NIO进行快速的文件拷贝
     */
    public void nioFileCopy(File in, File out) throws IOException {
        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        try {
            // original -- apparently has trouble copying large files on Windows
            // inChannel.transferTo(0, inChannel.size(), outChannel);
            // magic number for Windows, 64Mb - 32Kb)
            int maxCount = (64 * 1024 * 1024) - (32 * 1024);
            long size = inChannel.size();
            long position = 0;
            while (position < size) {
                position += inChannel.transferTo(position, maxCount, outChannel);
            }
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    /**
     * 创建图片文件的缩略图
     *
     * @param filename    图片文件名
     * @param thumbWidth  缩略图宽度
     * @param thumbHeight 缩略图高度
     * @param quality     质量
     * @param outFilename 输出文件名
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void createThumbnail(String filename, int thumbWidth, int thumbHeight, int quality, String outFilename)
            throws InterruptedException, FileNotFoundException, IOException {
        // load image from filename
        Image image = Toolkit.getDefaultToolkit().getImage(filename);
        MediaTracker mediaTracker = new MediaTracker(new Container());
        mediaTracker.addImage(image, 0);
        mediaTracker.waitForID(0);
        // use this to test for errors at this point: System.out.println(mediaTracker.isErrorAny());

        // determine thumbnail size from WIDTH and HEIGHT
        double thumbRatio = (double) thumbWidth / (double) thumbHeight;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double imageRatio = (double) imageWidth / (double) imageHeight;
        if (thumbRatio < imageRatio) {
            thumbHeight = (int) (thumbWidth / imageRatio);
        } else {
            thumbWidth = (int) (thumbHeight * imageRatio);
        }

        // draw original image to thumbnail image object and
        // scale it to the new size on-the-fly
        BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = thumbImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);

        // save thumbnail image to outFilename
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFilename));
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
        quality = Math.max(0, Math.min(quality, 100));
        param.setQuality((float) quality / 100.0f, false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(thumbImage);
        out.close();
    }

//    /**
//     * 利用iText库创建PDF文件
//     */
//    public void generatePdf() {
//        try {
//            OutputStream file = new FileOutputStream(new File("C:\\Test.pdf"));
//            Document document = new Document();
//            PdfWriter.getInstance(document, file);
//            document.open();
//            document.add(new Paragraph("Hello Kiran"));
//            document.add(new Paragraph(new Date().toString()));
//            document.close();
//            file.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * HTTP 代理设置
     */
    public void httpProxy() {
        System.getProperties().put("http.proxyHost", "someProxyURL");
        System.getProperties().put("http.proxyPort", "someProxyPort");
        System.getProperties().put("http.proxyUser", "someUserName");
        System.getProperties().put("http.proxyPassword", "somePassword");
    }

    /**
     * 抓取屏幕
     *
     * @param fileName 输出的屏幕截图文件名
     * @throws Exception
     */
    public void captureScreen(String fileName) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        ImageIO.write(image, "png", new File(fileName));
    }

    /**
     * 列出文件和目录
     */
    public void listDirAndFiles() {
        File dir = new File("directoryName");
        String[] children = dir.list();
        if (children == null) {
            // Either dir does not exist or is not a directory
        } else {
            for (String filename : children) {
                // Get filename of file or directory
            }
        }

        // It is also possible to filter the list of returned files.
        // This example does not return any files that start with `.'.
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        };
        children = dir.list(filter);

        // The list of files can also be retrieved as File objects
        File[] files = dir.listFiles();

        // This filter only returns directories
        FileFilter fileFilter = new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
        files = dir.listFiles(fileFilter);
    }

    public void createZipOrJarFile() throws IOException {
        String[] args = new String[]{"", "", ""};
        if (args.length < 2) {
            System.err.println("usage: java ZipIt Zip.zip file1 file2 file3");
            System.exit(-1);
        }
        File zipFile = new File(args[0]);
        if (zipFile.exists()) {
            System.err.println("Zip file already exists, please try another");
            System.exit(-2);
        }
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);
        int bytesRead;
        byte[] buffer = new byte[1024];
        CRC32 crc = new CRC32();
        for (int i = 1, n = args.length; i < n; i++) {
            String name = args[i];
            File file = new File(name);
            if (!file.exists()) {
                System.err.println("Skipping: " + name);
                continue;
            }
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));
            crc.reset();
            while ((bytesRead = bis.read(buffer)) != -1) {
                crc.update(buffer, 0, bytesRead);
            }
            bis.close();
            // Reset to beginning of input stream
            bis = new BufferedInputStream(
                    new FileInputStream(file));
            ZipEntry entry = new ZipEntry(name);
            entry.setMethod(ZipEntry.STORED);
            entry.setCompressedSize(file.length());
            entry.setSize(file.length());
            entry.setCrc(crc.getValue());
            zos.putNextEntry(entry);
            while ((bytesRead = bis.read(buffer)) != -1) {
                zos.write(buffer, 0, bytesRead);
            }
            bis.close();
        }
        zos.close();
    }

    /**
     * 解析/读取XML 文件
     *
     * @param fileName xml文件名
     */
    public void parseXmlFile(String fileName) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(fileName);
            if (file.exists()) {
                Document doc = db.parse(file);
                Element docEle = doc.getDocumentElement();

                // Print root element of the document
                System.out.println("Root element of the document: " + docEle.getNodeName());
                NodeList studentList = docEle.getElementsByTagName("student");

                // Print total student elements in document
                System.out.println("Total students: " + studentList.getLength());

                if (studentList != null && studentList.getLength() > 0) {
                    for (int i = 0; i < studentList.getLength(); i++) {

                        Node node = studentList.item(i);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            System.out.println("=====================");
                            Element e = (Element) node;
                            NodeList nodeList = e.getElementsByTagName("name");
                            System.out.println("Name: "
                                    + nodeList.item(0).getChildNodes().item(0)
                                    .getNodeValue());
                            nodeList = e.getElementsByTagName("grade");
                            System.out.println("Grade: "
                                    + nodeList.item(0).getChildNodes().item(0)
                                    .getNodeValue());
                            nodeList = e.getElementsByTagName("age");
                            System.out.println("Age: "
                                    + nodeList.item(0).getChildNodes().item(0)
                                    .getNodeValue());
                        }
                    }
                } else {
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * 把 Array 转换成 Map
     */
    public void arrayToMap() {
        String[][] countries = {{"United States", "New York"}, {"United Kingdom", "London"},
                {"Netherland", "Amsterdam"}, {"Japan", "Tokyo"}, {"France", "Paris"}};
        Map countryCapitals = ArrayUtils.toMap(countries);
        System.out.println("Capital of Japan is " + countryCapitals.get("Japan"));
        System.out.println("Capital of France is " + countryCapitals.get("France"));
    }

    /**
     * 发送邮件
     * import javax.mail.*;
     * import javax.mail.internet.*;
     */
//    public void postMail(String recipients[], String subject, String message, String from) throws MessagingException {
//        boolean debug = false;
//
//        //Set the host smtp address
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.example.com");
//
//        // create some properties and get the default Session
//        Session session = Session.getDefaultInstance(props, null);
//        session.setDebug(debug);
//
//        // create a message
//        Message msg = new MimeMessage(session);
//
//        // set the from and to address
//        InternetAddress addressFrom = new InternetAddress(from);
//        msg.setFrom(addressFrom);
//
//        InternetAddress[] addressTo = new InternetAddress[recipients.length];
//        for (int i = 0; i < recipients.length; i++) {
//            addressTo[i] = new InternetAddress(recipients[i]);
//        }
//        msg.setRecipients(Message.RecipientType.TO, addressTo);
//
//        // Optional : You can also set your custom headers in the Email if you Want
//        msg.addHeader("MyHeaderName", "myHeaderValue");
//
//        // Setting the Subject and Content Type
//        msg.setSubject(subject);
//        msg.setContent(message, "text/plain");
//        Transport.send(msg);
//    }

    /**
     * 发送HTTP 请求
     */
    public void sendHttpRequest() {
        try {
            URL my_url = new URL("http://coolshell.cn/");
            BufferedReader br = new BufferedReader(new InputStreamReader(my_url.openStream()));
            String strTemp = "";
            while(null != (strTemp = br.readLine())){
                System.out.println(strTemp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 改变数组的大小
     * @param oldArray 旧的数组
     * @param newSize 新的数组大小
     * @return 新的数组
     */
    public static Object resizeArray (Object oldArray, int newSize) {
        int oldSize = java.lang.reflect.Array.getLength(oldArray);
        Class elementType = oldArray.getClass().getComponentType();
        Object newArray = java.lang.reflect.Array.newInstance(
                elementType,newSize);
        int preserveLength = Math.min(oldSize,newSize);
        if (preserveLength > 0)
            System.arraycopy (oldArray,0,newArray,0,preserveLength);
        return newArray;
    }

}
