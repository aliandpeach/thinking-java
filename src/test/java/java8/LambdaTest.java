package java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * LambdaTest
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2017/10/21
 */
public class LambdaTest {
    @Test
    public void testConsumer() {
        forEach(new ArrayList<Integer>(){{add(1);}}, System.out::println);
    }

    public <R> void forEach(List<R> list, Consumer<R> c) {
        for (R t : list) {
            c.accept(t);
        }
    }
}
