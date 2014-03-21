package ch20annotation.simple;

/**
 * 测试类.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 14-3-21
 */
public class Test {
    public static void main(String[] args) throws Exception {
        AnnotationParser parse = new AnnotationParser();
        Object[] params = new Object[10];
        params[0] = "123@qq.com";
        UseCase useCase = new UseCase();
        parse.parseMethod(useCase, UseCase.class.getDeclaredMethods()[0], params);
    }
}
