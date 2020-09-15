package chapter8;

import chapter6.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

public class ImperativeToStreams {
    public static void main(String[] args) {
        List<String> dishNames = new ArrayList<>();

        for(Dish dish : Dish.menu) {
            if (dish.getCalories() > 300) { // filtering
                dishNames.add(dish.getName()); // extracting
            }
        }

        System.out.println(dishNames);
        dishNames = Dish.menu.stream()
                             .filter(d -> d.getCalories() > 300)
                             .map(Dish::getName)
                             .collect(toList());
        System.out.println(dishNames);
    }
}
