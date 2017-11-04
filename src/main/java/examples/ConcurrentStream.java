package examples;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 并行流测试
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2017/11/4
 */
public class ConcurrentStream {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void sequentialSum1() {
        sequentialSum(1000000L);
    }
    public long sequentialSum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void parallelSum1() {
        parallelSum(1000000L);
    }

    public long parallelSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(ConcurrentStream.class.getSimpleName())
                .forks(1)
                .warmupIterations(5) //预热次数
                .measurementIterations(5) //真正执行次数
                .build();

        new Runner(opt).run();
    }
}
