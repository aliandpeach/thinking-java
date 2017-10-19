package ch14reflect;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static mindview.Print.print;
import static mindview.Print.printnb;

public class ReflectDemo {

    /**
     * @param t
     * @param <T>
     */
    public <T> void showMethods(T t) {
        Class<?> c = t.getClass();
        Method[] methods = c.getMethods();
        Constructor[] constructors = c.getConstructors();
        for (Method m : methods) {
            printnb(m.toString());
            print();
        }
        for (Constructor constructor : constructors) {
            printnb(constructor.toGenericString());
            print();
        }
    }


    public <T> void showFields(T t) throws Exception {
        Class<?> c = t.getClass();
        Field[] fields = c.getDeclaredFields();
        System.out.println(fields.length);
        List<Class> ccc = new ArrayList<Class>();
        ccc.add(Integer.TYPE);
        ccc.add(Boolean.TYPE);
        ccc.add(Byte.TYPE);
        ccc.add(Short.TYPE);
        ccc.add(Float.TYPE);
        ccc.add(Double.TYPE);
        ccc.add(Character.TYPE);
        ccc.add(Long.TYPE);
        ccc.add(String.class);
        ccc.add(Integer.class);
        ccc.add(Boolean.class);
        ccc.add(Byte.class);
        ccc.add(Short.class);
        ccc.add(Float.class);
        ccc.add(Double.class);
        ccc.add(Character.class);
        ccc.add(Long.class);
        ccc.add(java.security.ProtectionDomain.class);
        ccc.add(sun.reflect.ReflectionFactory.class);
        ccc.add(sun.reflect.annotation.AnnotationType.class);
        ccc.add(sun.util.calendar.BaseCalendar.class);
        for (Field f : fields) {
            if (Modifier.isTransient(f.getModifiers())) {
                continue;
            }
            if (f.getType().isInterface()) {
                continue;
            }
            Class clazz = f.getType();
            if (ccc.contains(clazz)) {
                continue;
            }
            if (!Serializable.class.isAssignableFrom(f.getType())) {
                System.out.println("error !!! ");
                System.out.println(f.getType().getName() + " : " + f.getName());
                System.exit(1);
            }
            if (f.getType().getDeclaredFields().length > 0) {
                showFields(f.getType());
            }
        }
    }
}
