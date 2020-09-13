package chapter6;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class GroupingBy {
    public enum CaloricLevel {
        DIET, NORMAL, FAT
    }

    public static void main (String[] args)
    {
        // GroupingBy using existing classification property
        Map<Dish.Type, List<Dish>> dishesByType = Dish.menu.stream().collect(groupingBy(Dish::getType));
        System.out.println(dishesByType);

        // GroupingBy using a made up classification property
        Map<CaloricLevel, List<Dish>> dishesByCalories = Dish.menu.stream().collect(groupingBy(
                (Dish d) -> {
                    if (d.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    }
                    else if (d.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    }
                    else {
                        return CaloricLevel.FAT;
                    }
                }
        ));
        System.out.println(dishesByCalories);

        // groupingBy(ClassificationFunction, collector) - the second argument can be used to achieve
        // different kinds of groupings, etc as shown below.

        // Multilevel grouping - using the 2 argument groupingBy(, 2nd argument is a collector)
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> mapByTypeByCaloricLevel = Dish.menu.stream().collect(
                groupingBy(Dish::getType, groupingBy((Dish d) -> {
                    if (d.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    }
                    else if (d.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    }
                    else {
                        return CaloricLevel.FAT;
                    }
                })));
        System.out.println(mapByTypeByCaloricLevel);

        // countByType
        Map<Dish.Type, Long> countByType = Dish.menu.stream().collect(groupingBy(Dish::getType, counting()));
        System.out.println(countByType);


        // maxDishBy Type
        // groupingBy is lazy constructor - there will be no Key with Optional.empty() value
        Map<Dish.Type, Optional<Dish>> maxDishByType = Dish.menu.stream().collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
        System.out.println(maxDishByType);

        // sum calories by type
        Map<Dish.Type, Integer> caloriesByType = Dish.menu.stream().collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
        System.out.println(caloriesByType);

        // Adapting the results from collector, 2nd argument of groupingBy()
        // using collectingAndThen(collector, transformationFunction)
        // also, its safe to do so, since groupingBy is lazy and there will never be Optional.empty()
        Map<Dish.Type, Dish> maxDishByTypeMap= Dish.menu.stream().collect(groupingBy(Dish::getType,
            collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(maxDishByTypeMap);

        // mapping(transformationFunction, collector to accumulate elements in the stream)
        // Caloric Levels Available by dish type
        // No guarantee about the type of set returned, use toCollection(HashSet::new) instead
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelByType = Dish.menu.stream().collect(groupingBy(Dish::getType,
                mapping((Dish d) -> {
                    if (d.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    }
                    else if (d.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    }
                    else {
                        return CaloricLevel.FAT;
                    }
                }, toSet())));
        System.out.println(caloricLevelByType);
    }
}
