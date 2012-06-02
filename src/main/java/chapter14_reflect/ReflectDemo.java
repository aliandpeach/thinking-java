package chapter14_reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import static net.mindview.util.Print.print;
import static net.mindview.util.Print.printnb;

public class ReflectDemo {

    /**
     *
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
}
