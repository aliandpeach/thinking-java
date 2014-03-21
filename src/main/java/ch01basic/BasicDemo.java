package ch01basic;

import static net.mindview.util.Print.print;

public class BasicDemo {
    /**
     * 整数的二进制补码表示
     * int 是32位的
     */
    public void binaryString() {
        int i = -12;
        // 定义一个string
        String str = "this is a string";
        System.out.println(Integer.toBinaryString(i));
    }

    /**
     * continue模拟goto
     */
    public void labeledFor() {
        int i = 0;
        outer:
        // Can't have statements here
        for (; true; ) { // infinite loop
            inner:
            // Can't have statements here
            for (; i < 10; i++) {
                print("i = " + i);
                if (i == 2) {
                    print("continue");
                    continue;
                }
                if (i == 3) {
                    print("break");
                    i++; // Otherwise i never
                    // gets incremented.
                    break;
                }
                if (i == 7) {
                    print("continue outer");
                    i++; // Otherwise i never
                    // gets incremented.
                    continue outer;
                }
                if (i == 8) {
                    print("break outer");
                    break outer;
                }
                for (int k = 0; k < 5; k++) {
                    if (k == 3) {
                        print("continue inner");
                        continue inner;
                    }
                }
            }
        }
        // Can't break or continue to labels here
    }

    /**
     * 测试Class对象是什么东东
     */
    public <T> boolean whatClass(T t) {
        return t.getClass() instanceof Class;
    }
}
