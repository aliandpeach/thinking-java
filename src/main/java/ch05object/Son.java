package ch05object;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2014/10/21
 */
public class Son extends Parent {

    public Son(String name) {
        super(name);
        say();
    }

    public void say() {
        System.out.println("son say...");
    }

    public static void main(String[] args) {
        new Son("LiLei");
    }
}
