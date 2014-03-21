package ch20annotation.simple;

import java.lang.reflect.Method;

/**
 * 注解解析类，非常关键
 *
 * @author XiongNeng
 * @version 1.0
 * @since 14-3-21
 */
public class AnnotationParser {

    /**
     * 功能描述: <br>
     * <p/>
     * 〈调用指定类的指定方法，传参〉
     */

    public void parseMethod(Object proxy, Method method, Object[] args) throws Exception {
        Regex df = method.getAnnotation(Regex.class);
        String name = "";
        if (df != null) {
            name = df.regexRule().value;
            method.invoke(proxy, args[0], name);
        }
    }
}
