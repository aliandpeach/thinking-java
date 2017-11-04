package java8.chap7;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 并行流测试
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2017/11/4
 */
public class ConcurrentStreamTest {

    public long sequentialSum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }

    public long parallelSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }

}
