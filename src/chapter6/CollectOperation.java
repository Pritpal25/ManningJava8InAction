package chapter6;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class CollectOperation
{

    public static void main(String[] args)
    {
        // Mapping
        Map<Dish.Type, List<Dish>> dishesByType = Dish.menu.stream()
                                                            .collect(groupingBy(Dish::getType));

        dishesByType.forEach((Dish.Type type, List<Dish> dishes) -> {
            System.out.print(type + " ");
            System.out.println(dishes.size());
        });

        // Count, Max and Min
        Long count = Dish.menu.stream().collect(counting());

        Comparator<Dish> dishComparator = Comparator.comparingInt(Dish::getCalories);


        Optional<Dish> minDish = Dish.menu.stream().collect(minBy(dishComparator));
        minDish.ifPresent(System.out::println);

        Optional<Dish> maxDish = Dish.menu.stream().collect(maxBy(dishComparator));
        maxDish.ifPresent(System.out::println);

        // Summarization
        int totalCalories = Dish.menu.stream().collect(summingInt(Dish::getCalories));
        System.out.println(totalCalories);

        Double avgCalories = Dish.menu.stream().collect(averagingInt(Dish::getCalories));
        System.out.println(avgCalories);

        IntSummaryStatistics intSummaryStatistics = Dish.menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(intSummaryStatistics);

        // Joining
        String allNames = Dish.menu.stream().map(Dish::getName).collect(joining(", "));
        System.out.println(allNames);

        // reducing, as a generalization to all the above methods
        // total calories
        int total = Dish.menu.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i+j));
        System.out.println(total);

        // minDish
        Dish.menu.stream().collect(reducing((Dish d1, Dish d2) -> d1.getCalories() < d2.getCalories() ? d1 : d2)).ifPresent(System.out::println);

        // counting using reducing
        Long totalCount = Dish.menu.stream().collect(reducing(0L, e -> 1L, Long::sum));
        System.out.println(totalCount);

        // summing - different ways

        int tCal = Dish.menu.stream().collect(summingInt(Dish::getCalories));
        Optional<Integer> toCal = Dish.menu.stream().map(Dish::getCalories).reduce(Integer::sum);
        int totCal = Dish.menu.stream().mapToInt(Dish::getCalories).sum();
    }
}
