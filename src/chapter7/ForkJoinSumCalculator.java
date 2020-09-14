package chapter7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSumCalculator extends RecursiveTask<Long>{
    private long[] array;
    private int start;
    private int end;
    private static int SPLIT_THRESHOLD = 100;

    public ForkJoinSumCalculator(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start + 1 > SPLIT_THRESHOLD) {
            int mid = (start+end)/2;
            ForkJoinSumCalculator f1 = new ForkJoinSumCalculator(array, start, mid);
            f1.fork(); // asynchronous left task execution

            ForkJoinSumCalculator f2 = new ForkJoinSumCalculator(array, mid + 1, end);
            Long f2Result = f2.compute(); // synchronous right task execution

            // This is a blocking call, hence why we started compute on f2 first.
            Long f1Result = f1.join(); // retrieving/waiting for result of first subtask.

            return f1Result + f2Result;
        }
        else {
            long result = 0L;
            for (int i = start; i <= end; i++) {
                result += array[i];
            }

            return result;
        }
    }

    public static void main(String[] args) {
        long[] numbers = LongStream.rangeClosed(1, 1000).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers, 0, numbers.length-1);
        System.out.println(new ForkJoinPool().invoke(task));
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
