package chapter06_basic.dom4j;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-23
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates
 */
public class UsingIterators {
    public void bar(Document document) throws DocumentException {

        Element root = document.getRootElement();

        // iterate through child elements of root
        for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            // do something
        }

        // iterate through child elements of root with element name "foo"
        for ( Iterator i = root.elementIterator( "foo" ); i.hasNext(); ) {
            Element foo = (Element) i.next();
            // do something
        }

        // iterate through attributes of root
        for ( Iterator i = root.attributeIterator(); i.hasNext(); ) {
            Attribute attribute = (Attribute) i.next();
            // do something
        }
    }
}
