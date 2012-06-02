package chapter06_basic;

import typeinfo.Book;

/**
 * Created with IntelliJ IDEA.
 * User: yidao
 * Date: 12-5-27
 * Time: 下午9:38
 * ======================================
 * Java中finalize示例
 * finalize()方法用途，在使用本地方法时候不是用new分配对象内存，
 * 所有需要再finalize()中用本地方法比如C语言的free()去释放内存
 */
public class Finalize {

    /**
     * 测试下finalize用法
     */
    public void fi() {
        Book novel = new Book(true);
        // Proper cleanup:
        novel.checkIn();
        // Drop the reference, forget to clean up:
        new Book(true);
        // Force garbage collection & finalization:
        System.gc();
        /*
        * Output: Error: checked out
        */// :~
    }
}




