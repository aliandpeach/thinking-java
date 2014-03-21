package ch21concurrent;//: concurrency/CallableDemo.java

import java.util.concurrent.*;
import java.util.*;

/**
 * 有返回值的线程Callable示例
 */
public class CallableDemo {
    public static void callable() {
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<String>> results = new ArrayList<Future<String>>();
        for (int i = 0; i < 10; i++)
            // results.add(exec.submit(new TaskWithResult(i)));
            try {
                // get() blocks until completion:
                System.out.println(exec.submit(new TaskWithResult(i)).get());
            } catch (InterruptedException e) {
                System.out.println(e);
                return;
            } catch (ExecutionException e) {
                System.out.println(e);
            } finally {
                // exec.shutdown();
            }
        exec.shutdown();
        // for (Future<String> fs : results)
        // try {
        // // get() blocks until completion:
        // System.out.println(fs.get());
        // } catch (InterruptedException e) {
        // System.out.println(e);
        // return;
        // } catch (ExecutionException e) {
        // System.out.println(e);
        // } finally {
        // exec.shutdown();
        // }
    }
} /*
 * Output: result of TaskWithResult 0 result of TaskWithResult 1 result of
 * TaskWithResult 2 result of TaskWithResult 3 result of TaskWithResult 4 result
 * of TaskWithResult 5 result of TaskWithResult 6 result of TaskWithResult 7
 * result of TaskWithResult 8 result of TaskWithResult 9
 */// :~

class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    public String call() {
        return "result of TaskWithResult " + id;
    }
}
