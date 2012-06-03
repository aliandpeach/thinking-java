package chapter11_collection;//: containers/Unsupported.java
// Unsupported operations in Java containers.

import java.util.*;

public class Unsupported {
    /**
     * Arrays.asList()返回大小不可变得List
     * Collections.unmodifiableList()返回内容和大小都不能变得List
     * @param msg
     * @param list
     */
    private void unsupport(String msg, List<String> list) {
        System.out.println("--- " + msg + " ---");
        Collection<String> c = list;
        Collection<String> subList = list.subList(1, 8);
        // Copy of the sublist:
        Collection<String> c2 = new ArrayList<String>(subList);
        try {
            c.retainAll(c2);
        } catch (Exception e) {
            System.out.println("retainAll(): " + e);
        }
        try {
            c.removeAll(c2);
        } catch (Exception e) {
            System.out.println("removeAll(): " + e);
        }
        try {
            c.clear();
        } catch (Exception e) {
            System.out.println("clear(): " + e);
        }
        try {
            c.add("X");
        } catch (Exception e) {
            System.out.println("add(): " + e);
        }
        try {
            c.addAll(c2);
        } catch (Exception e) {
            System.out.println("addAll(): " + e);
        }
        try {
            c.remove("C");
        } catch (Exception e) {
            System.out.println("remove(): " + e);
        }
        // The List.set() method modifies the value but
        // doesn't change the size of the data structure:
        try {
            list.set(0, "X");
        } catch (Exception e) {
            System.out.println("List.set(): " + e);
        }
    }

    /**
     * 验证
     */
    public void twoList() {
        List<String> list = Arrays.asList("A B C D E F G H I J K L".split(" "));
        unsupport("Modifiable Copy", new ArrayList<String>(list));
        unsupport("Arrays.asList()", list);
        unsupport("unmodifiableList()",
                Collections.unmodifiableList(new ArrayList<String>(list)));
    }
} /*
 * Output: --- Modifiable Copy --- --- Arrays.asList() --- retainAll():
 * java.lang.UnsupportedOperationException removeAll():
 * java.lang.UnsupportedOperationException clear():
 * java.lang.UnsupportedOperationException add():
 * java.lang.UnsupportedOperationException addAll():
 * java.lang.UnsupportedOperationException remove():
 * java.lang.UnsupportedOperationException --- unmodifiableList() ---
 * retainAll(): java.lang.UnsupportedOperationException removeAll():
 * java.lang.UnsupportedOperationException clear():
 * java.lang.UnsupportedOperationException add():
 * java.lang.UnsupportedOperationException addAll():
 * java.lang.UnsupportedOperationException remove():
 * java.lang.UnsupportedOperationException List.set():
 * java.lang.UnsupportedOperationException
 */// :~
