package ch20annotation.display;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 14-3-24
 */
public class DisplayUtil {

    static Set<Class> classes = new HashSet<Class>() {{
        add(Integer.TYPE);
        add(Integer.class);
        add(Long.TYPE);
        add(Long.class);
        add(String.class);
        add(Short.TYPE);
        add(Short.class);
        add(Character.TYPE);
        add(Character.class);
        add(Byte.TYPE);
        add(Byte.class);
        add(Float.TYPE);
        add(Float.class);
        add(Double.TYPE);
        add(Double.class);
        add(Boolean.TYPE);
        add(Boolean.class);
    }};

    public static void feedList(List<MonitorNode> disList, List<MonitorNode> thrList, Object val) {
        Class cl = val.getClass();
        try {
            for (Field f : cl.getDeclaredFields()) {
                if (classes.contains(f.getType()) || List.class.isInstance(f.get(val))) {
                    Display display = f.getAnnotation(Display.class);
                    if (display != null) {
                        if (display.threshold() >= 0) {
                            thrList.add(new MonitorNode(
                                    display.name(), String.valueOf(f.get(val)), display.threshold()));
                        } else {
                            disList.add(new MonitorNode(display.name(), String.valueOf(f.get(val)), -1));
                        }
                    }
                } else {
                    feedList(disList, thrList, f.get(val));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static class MonitorNode {
        public String name;
        public String value;
        public double threshold;

        public MonitorNode(String name, String value, double threshold) {
            this.name = name;
            this.value = value;
            this.threshold = threshold;
        }
    }

    public static void main(String[] args) {
        Dea.Logging ll = new Dea.Logging();
        ll.filename = "ll.filename";
        ll.level = "INFO";

        Dea.Config cc = new Dea.Config();
        cc.logging = ll;
        cc.price = 22.22;
        cc.request_name = "request_name";
        cc.years = 4444L;
        Dea dea = new Dea();
        dea.beautiful = true;
        dea.config = cc;
        dea.core = 1;
        dea.cpu = 0.2;
        dea.name = "dea name";
        dea.uuid = "ADGDDDDDDDDDDDDDDXX";
        dea.uris = new ArrayList<String>() {{
            add("api.orchard.com");
            add("ddd");
        }};

        List<MonitorNode> dList = new ArrayList<>();
        List<MonitorNode> sList = new ArrayList<>();
        feedList(dList, sList, dea);
        for (MonitorNode node : dList) {
            System.out.println(node.name);
            System.out.println(node.value);
        }
        System.out.println("======================================");
        for (MonitorNode node : sList) {
            System.out.println(node.name);
            System.out.println(node.value);
            System.out.println(node.threshold);
        }
    }
}
