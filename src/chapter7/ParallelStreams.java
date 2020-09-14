package chapter7;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreams {
    public static class Accumulator {
        long total = 0L;
        public void sum(long n) {
            total = total + n;
        }
    }

    private static long iterativeSum(long n) {
        long result = 0L;
        for (long l = 1L; l <= n; l++){
            result = result + l;
        }

        return result;
    }

    // Autoboxing overheads
    private static long sequentialStream(long n) {
        return Stream.iterate(1L, i -> i+1)
                .limit(n)
                .reduce(0L, (i, j) -> Long.sum(i, j)); // this can be replaced with Long :: sum
    }

    // Internally, calling parallel() turns on a boolean indicating parallel execution of entire stream
    // pipeline. We can likewise call sequential(), whichever method is called last takes precedence for
    // the entire pipeline.
    // Runtime.getRuntime().availableProcessors() is the number of parallel threads that a parallel stream
    // operates on. This is internally managed by ForkJoinPool framework

    // Autoboxing overhead + overhead of trying to parallelize an inherently sequential iterate operation
    private static long parallelStream(long n) {
        return Stream.iterate(1L, i -> i+1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    // works on primitives and parallelization is possible since the range can be easily split
    private static long rangeStreamSum(long n) {
        return LongStream.range(1L, n)
                        .parallel()
                        .reduce(0L, (i, j) -> i+j);
    }

    // Every invocation of forEach mutates a shared state in total variable.
    private static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1L, n).parallel().forEach(accumulator::sum);
        return accumulator.total;
    }

    private static Long measureSumPerformance(Function<Long, Long> adder, long n) {
        long total_duration = 0L;
        for(int i = 1; i <= 10; i++) {
            long start_time = System.nanoTime();
            long sum = adder.apply(n);
            System.out.println(sum);
            long durationInMillis = (System.nanoTime() - start_time);
            total_duration += durationInMillis;
        }

        return total_duration;
    }
    public static void main(String[] args) {
        Long iterativeTime = measureSumPerformance(ParallelStreams::iterativeSum, 10_000_000); // fastest
        Long sequentialStream = measureSumPerformance(ParallelStreams::sequentialStream, 10_000_000); // slower
        Long parallelStream = measureSumPerformance(ParallelStreams::parallelStream, 10_000_000); // slowest
        System.out.println(iterativeTime + " " + sequentialStream + " " + parallelStream);
        Long rangeStreamTime = measureSumPerformance(ParallelStreams::rangeStreamSum, 10_000_000);
        System.out.println(rangeStreamTime);

        // For this part, comment everything above in main method
        // This demonstrates how using parallel while modifying a mutable state can lead to incorrect results,
        // much less better performance.
        System.out.println(measureSumPerformance(ParallelStreams::sideEffectSum, 10_000_000));
    }
}
