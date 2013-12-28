package chapter01_basic.dom4j;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-23
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates
 */
public class FastLooping {
    public void treeWalk(Document document) {
        treeWalk(document.getRootElement());
    }

    public void treeWalk(Element element) {
        for (int i = 0, size = element.nodeCount(); i < size; i++) {
            Node node = element.node(i);
            if (node instanceof Element) {
                treeWalk((Element) node);
            } else {
                System.out.println("else...");
            }
        }
    }
}
