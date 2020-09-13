package chapter6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {
    @Override
    public Supplier<List<T>> supplier() {
        return () -> new ArrayList<T>();
        // return ArrayList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return (list, item) -> list.add(item);
        // return List::add;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return (left, right) -> {
            left.addAll(right);
            return left;
        };
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        return (list) -> list;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.CONCURRENT, Characteristics.IDENTITY_FINISH));
    }

    public static void main(String[] args) {
        List<Dish> list = Dish.menu.stream().collect(new ToListCollector<>());
        System.out.println(list);

        // More concise collector definition without actual collector interface implementation
        // Not good form - confusing and does not promote re-use.
        // collect (supplier, accumulator, combiner) - characteristics are assumed to be IDENTITY and CONCURRENT
        List<Dish> list2 = Dish.menu.stream().collect(ArrayList::new, List::add, List::addAll);
        System.out.println(list2);
    }
}
