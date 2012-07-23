package chapter06_basic.dom4j;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-23
 * Time: 上午11:27
 * To change this template use File | Settings | File Templates
 */
public class NavigationWithXPath {
    //In dom4j XPath expressions can be evaluated on the Document or on any Node in the tree
    // (such as Attribute, Element or ProcessingInstruction).
    // This allows complex navigation throughout the document with a single line of code.
    public void bar(Document document) {
        List list = document.selectNodes("//foo/bar");
        Node node = document.selectSingleNode("//foo/bar/author");
        String name = node.valueOf("@name");
    }

    //For example if you wish to find all the hypertext links in
    // an XHTML document the following code would do the trick.
    public void findLinks(Document document) throws DocumentException {
        List list = document.selectNodes("//a/@href");
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Attribute attribute = (Attribute) iter.next();
            String url = attribute.getValue();
        }
    }
    //If you need any help learning the XPath language we highly
    // recommend the Zvon tutorial which allows you to learn by example.
}
