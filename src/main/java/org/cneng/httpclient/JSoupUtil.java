package org.cneng.httpclient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.DataInputStream;
import java.io.FileInputStream;

/**
 * 利用JSouup解析
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2015/2/4
 */
public class JSoupUtil {
    /**
     * 获取链接
     * @param htmlContent html
     * @return 链接
     * @throws Exception
     */
    public static String parseLink(String htmlContent) throws Exception {
        Document doc = Jsoup.parse(htmlContent);
        Elements content = doc.getElementsByAttributeValue("class", "font16");
        if (content.size() > 0) {
            Element e = content.get(0);
            System.out.println(e.child(0).attr("href"));
            return e.child(0).attr("href");
        }
        return null;
    }

    /**
     * 获取真实营业场所或地址
     * @param htmlContent html
     * @return 营业场所或地址
     * @throws Exception
     */
    public static String parseLocation(String htmlContent) throws Exception {
        Document doc = Jsoup.parse(htmlContent);
        Element location = doc.select(
                "table[class=detailsList]:eq(0) > tbody > tr >th:matches(所$):eq(0) + td").first();
        String result = location.text();
        System.out.println("location=" + result);
        return result;
    }

//    private static String parseLinkXpath(String htmlContent) throws Exception {
//        InputSource source = new InputSource(new StringReader(htmlContent));
//
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document document = db.parse(source);
//
//        XPathFactory xpathFactory = XPathFactory.newInstance();
//        XPath xpath = xpathFactory.newXPath();
//
//        String link = xpath.evaluate("//div[@class='list'][1]/ul/li[1]/a/@href", document);
//
//        System.out.println("link=" + link);
//        return link;
//    }

    public static void main(String[] args) throws Exception{
//        DataInputStream dis = new DataInputStream(new FileInputStream(
//                "D:\\work\\projects\\thinking-java\\src\\main\\resources\\test.html"));
//        byte[] datainBytes = new byte[dis.available()];
//        dis.readFully(datainBytes);
//        dis.close();
//        String content = new String(datainBytes, 0, datainBytes.length);
////        System.out.println(content);
//
//        parseLocation(content);
    }
}
