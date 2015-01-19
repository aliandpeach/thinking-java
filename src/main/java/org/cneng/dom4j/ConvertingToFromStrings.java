package org.cneng.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-23
 * Time: 上午11:31
 * To change this template use File | Settings | File Templates
 */
public class ConvertingToFromStrings {
    public static String documentToString(Document document) {
        return document.asXML();
    }

    public static Document stringToDocument(String str) throws DocumentException {
        String text = str;
        Document document = DocumentHelper.parseText(text);
        return document;
    }
}
