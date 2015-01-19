package org.cneng.xstream;

import com.thoughtworks.xstream.XStream;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-24
 * Time: 下午4:06
 * To change this template use File | Settings | File Templates
 */
public class XstreamTest {
    public static void main(String[] args) {
        objectToXml();
        xmlToObject();
    }

    private static void objectToXml() {
        XStream xstream = new XStream();
//        xstream.alias("person", Person.class);
//        xstream.alias("phonenumber", PhoneNumber.class);
        Person joe = new Person("Joe", "Walnes");
        joe.setPhone(new PhoneNumber(123, "1234-456"));
        joe.setFax(new PhoneNumber(123, "9999-999"));
        String xml = xstream.toXML(joe);
        System.out.println(xml);
    }

    private static void xmlToObject() {
        XStream xstream = new XStream();
//        xstream.alias("person", Person.class);
//        xstream.alias("phonenumber", PhoneNumber.class);
        URL url = Thread.currentThread().getContextClassLoader().getResource("chapter06_basic/xstream/person.xml");
        Person newJoe = (Person)xstream.fromXML(url);
        System.out.println(newJoe);
    }
}
