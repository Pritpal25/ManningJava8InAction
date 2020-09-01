package chapter5;

import java.util.function.Supplier;
import java.util.stream.Stream;

/*
Generating fibonnacci using :
1. Iterate method - stateless
2. Generate method - stateful.

Always prefer stateless methods to stateful ones since they are parallelizable.
 */

public class InfiniteStreams {
    public static void main(String[] args) {
        Stream.iterate(new int[]{0,1}, t -> new int[]{t[1], t[0] + t[1]})
              .map(t -> t[0])
              .limit(10)
              .forEach(System.out::println);

        Supplier<Integer> fibSupplier = new Supplier<Integer>() {
            int prev = 0;
            int curr = 1;
            @Override
            public Integer get() {
                int old_prev = prev;
                int next = prev + curr;
                prev = curr;
                curr = next;
                return old_prev;
            }
        };

        Stream.generate(fibSupplier)
              .limit(5)
              .forEach(System.out::println);
    }
}
