package ch11collection;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-6-2
 * Time: 上午9:04
 *
 */
public class CollectionBasic {

    /**
     * 往集合中add元素
     * 优先考虑Collections.addAll(Collection c, Object... objs)
     */
    public List<String> addAll() {
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, "aa", "bb", "cc");
        return list;
    }

    /**
     * Arrays.asList得到的List不能add，delete元素
     */
    public List<String> asList() {
        // 显示类型参数说明
        return Arrays.asList("1","2","3");
    }

    /**
     * ArrayList和LinkedList删除元素的效率比较
     */
    public void removeInArrayAndLinkList() {
        List<String> list = new ArrayList<String>();
        for (int i =0; i< 100000; i++) {
            list.add(""+i);
        }
        int j = list.size() -1;
        long start = System.currentTimeMillis();
        for (int i=0; i< 90000; i++) {
            list.remove(j--);
        }
        long end = System.currentTimeMillis();
        System.out.println("ArrayList删除时间：" + (end - start));

        list = new LinkedList<String>();
        for (int i =0; i< 100000; i++) {
            list.add(""+i);
        }
        j = list.size() -1;
        start = System.currentTimeMillis();
        for (int i=0; i< 90000; i++) {
            list.remove(j--);
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList删除时间：" + (end - start));
    }
}
