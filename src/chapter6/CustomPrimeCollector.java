package chapter6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public class CustomPrimeCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {
    private Boolean isPrime(List<Integer> primes, Integer candidate) {
        int sqrt = (int) Math.sqrt(candidate);

        for(int i = 0; i < primes.size(); i++) {
            if (primes.get(i) <= sqrt){
                if (candidate % primes.get(i) == 0) {
                    return false;
                }
            } else {
                break;
            }
        }

        return true;
    }

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> {
            Map<Boolean, List<Integer>> map = new HashMap<>();
            map.put(true, new ArrayList<>());
            map.put(false, new ArrayList<>());

            return map;
        };
    }

    // We are making an optimization here, we are only checking for divisors amongst the prime numbers
    // smaller than the sqrt of the candidate. Since, we did not have access to this list(accumulator)
    // in the factory collectors, we could not use any for this optimization, so we developed our own collector.
    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (map, integer) -> {
            map.get(isPrime(map.get(true), integer)).add(integer);
        };
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        // Our algorithm is sequential - so we should be NOT implementing this or throwing Not supported exception.
        /*return (map1, map2) -> {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));
            return map1;
        };*/
        throw new UnsupportedOperationException();
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return (map) -> map;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }

    public static void main(String[] args) {
        Map<Boolean, List<Integer>> primes = IntStream.rangeClosed(2, 100).boxed().collect(new CustomPrimeCollector());

        System.out.println(primes);

        // Possible to implement the above collector using the overloaded version of collect(supplier, accumulator, combiner)
        // Like we did in ToListCollector
    }
}
