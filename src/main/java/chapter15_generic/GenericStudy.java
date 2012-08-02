package chapter15_generic;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-6-20
 * Time: 下午5:55
 * GenericStudy
 */

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * JDK1.5 泛型
 *
 * @author yidao
 */
public class GenericStudy {

    public static void main(String[] args) throws Exception {
        // 第1点：Java的泛型仅仅只是通用Java的编译器实现的，进行全安检查，编译成class文件后没有保留泛型信息
        Collection<Integer> c1 = new ArrayList<Integer>();
        Collection<String> c2 = new ArrayList<String>();
        // 1.1 获取c1与c2的字节码看是否相同
        System.out.println("c1与c2的字节码是否相同：" + (c1.getClass() == c2.getClass()));// true
        c1.add(23);
        // 1.2 通用反射我们可以向c1中添加String类型的数据（class文件没有任何泛型）
        Class clazz = c1.getClass();
        Method collAddMethod = clazz.getMethod("add", Object.class);
        collAddMethod.invoke(c1, "http://zmx.iteye.com");
        for (Object obj : c1) {
            System.out.println(obj);
        }

        // 泛型中的？通配符：表示任何类型，它与Object是有区别的如下所示：
        // collection1可以存放任保类型，而collection2则只能放在Object类型
        Collection<?> collection1 = new ArrayList<String>();
        collection1 = new ArrayList<Integer>();
        collection1 = new ArrayList<Object>();
        Collection<Object> collection2 = new ArrayList<Object>();
        // 泛型中的向上或向下限定
        // collection3表示它可以存放Number或Number的子类
        Collection<? extends Number> collection3 = null;
        collection3 = new ArrayList<Number>();
        collection3 = new ArrayList<Double>();
        collection3 = new ArrayList<Long>();
        // collection4表示它可以存放Integer或Integer的父类
        Collection<? super Integer> collection4 = null;
        collection4 = new ArrayList<Object>();

        // 泛型简单使用（Map.Entry是Map的一个内部类，表法Map中存在的一个对象）
        Map<String, Integer> testMap = new HashMap<String, Integer>();
        testMap.put("xiaobojava", 2);
        testMap.put("mengya", 3);
        testMap.put("zmx", 6);
        Set<Map.Entry<String, Integer>> entrySet = testMap.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            System.out.println(entry.getKey() + "：" + entry.getValue());
        }

        // 自定义泛型
        String[] a = {"aa", "bb", "cc"};
        swap(a, 1, 2);
        Integer[] b = {32, 45, 67};
        swap(b, 1, 2);
        // 泛型中的类型不能为基本数据类型（如下就不行）
        int[] c = {1, 3, 5};
        // swap(c,1,2);

        Vector<Date> v1 = new Vector<Date>();
        // 由于泛型是由编译器实现的语法检查，编译成class文件后没有保留泛型信息，但可以根据Method获取
        Method applyMethod = GenericStudy.class.getMethod("applyVector",
                Vector.class);
        // getGenericParameterTypes()按照声明顺序返回 Type 对象的数组，这些对象描述了此 Method
        // 对象所表示的方法的形参类型的。
        Type[] type = applyMethod.getGenericParameterTypes();
        // ParameterizedType extends Type
        ParameterizedType pType = (ParameterizedType) type[0];// applyVector只用一个参数
        // getRawType()返回 Type 对象，表示声明此类型的类或接口
        System.out.println("getRawType()方法：表示声明此类型的类或接口是：" + pType.getRawType());
        // getActualTypeArguments()返回表示此类型实际类型参数的 Type 对象的数组。
        System.out.println("getActualTypeArguments()方法：实际类型参数的 Type 对象"
                + pType.getActualTypeArguments()[0]);// Vector<Date>只用一个Date类型

    }

    public static void applyVector(Vector<Date> v1) {

    }

    public static void printCollection(Collection<?> collection) {
        for (Object obj : collection) {
            System.out.println(obj);
        }
    }

    /**
     * 自定义泛型一个泛型方法
     *
     * @param <T> 表示自己定义的一个泛型类型（T不能是基本数据类型）
     * @param a   T类型的数组
     * @param m   T类型的数组m位置
     * @param n   T类型的数组n位置
     */
    public static <T> void swap(T[] a, int m, int n) {
        T temp = a[n];
        a[n] = a[m];
        a[m] = temp;
    }

    /**
     * 将某个类型的数组元素copy到这个类型集合
     *
     * @param <T> 表示自己定义的一个泛型类型（T不能是基本数据类型）
     * @param a   集合
     * @param b   数组
     */
    public <T> void copyArray2Collection(Collection<T> a, T[] b) {

    }

}