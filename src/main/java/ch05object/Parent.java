package ch05object;

/**
 * Description of this file.
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2014/10/21
 */
public class Parent {
    private String name;

    public Parent(String name) {
        this.name = name;
        this.say();
    }

    public void say() {
        System.out.println("parent say...");
    }


    /**
     * 设置 name.
     *
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }
}
