package chapter6;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class PartitioningBy {

    public static boolean isPrime(int n){
        int sqrt = (int) Math.sqrt(n);
        return IntStream.rangeClosed(2, sqrt).noneMatch(i -> (n % i == 0));
    }

    public static void main(String[] args)
    {
        // partitioningBy(partitioning function - predicate) - this is a special case of groupingBy where the key is the predicate
        // supplied to partitioningBy method.
        Map<Boolean, List<Dish>> partitionedMenu = Dish.menu.stream().collect(partitioningBy(Dish::isVegetarian));
        List<Dish> vegetarian = partitionedMenu.get(true);
        System.out.println(vegetarian);

        //Simpler way for above
        List<Dish> vegDishes = Dish.menu.stream().filter(Dish::isVegetarian).collect(toList());
        System.out.println(vegDishes);

        // partitioning with grouping
        Map<Boolean, Map<Dish.Type, List<Dish>>> vegNonVegByType = Dish.menu.stream().collect(
                partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType))
        );
        System.out.println(vegNonVegByType);

        // Finding most caloric veg and non veg dish
        Map<Boolean, Dish> mostCaloricDish = Dish.menu.stream().collect(
                partitioningBy(Dish::isVegetarian,
                        collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get))
        );
        System.out.println(mostCaloricDish);

        // Prime, NonPrime partitioning
        Map<Boolean, List<Integer>> primePartitioning = IntStream.rangeClosed(2, 50)
                .boxed()
                .collect(partitioningBy(i -> isPrime(i)));
        System.out.println(primePartitioning);
    }


}
