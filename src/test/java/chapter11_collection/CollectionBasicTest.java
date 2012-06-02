package chapter11_collection;

import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-2
 * Time: 上午9:09
 * CollectionBasicTest
 */
public class CollectionBasicTest {
    private CollectionBasic collectionBasic;
    @Before
    public void setUp() throws Exception {
        collectionBasic = new CollectionBasic();
    }

    @Test
    public void testAddAll() throws Exception {
        System.out.println(collectionBasic.addAll());
    }

    @Test
    public void testAsList() {
        System.out.println(collectionBasic.asList());
    }

    @Test
    public void testRemoveInArrayAndLinkList() throws Exception {
        collectionBasic.removeInArrayAndLinkList();
    }
}
