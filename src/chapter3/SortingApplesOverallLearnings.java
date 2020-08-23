package chapter3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

public class SortingApplesOverallLearnings {

    public static class AppleComparator implements Comparator<Apple> {
        public int compare(Apple a, Apple b) {
            return Integer.compare(a.getWeight(), b.getWeight());
        }
    }

    public static void main(String[] args) {
        List<Apple> apples = Arrays.asList(new Apple(200, "Red"), new Apple(150, "Green"));

        // Method 1 - Using new comparator
        apples.sort(new AppleComparator());
        System.out.println(apples);

        // Method 2 - Using Anonymous class
        apples.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple a, Apple b) {
                return Integer.compare(b.getWeight(), a.getWeight());
            }
        });
        System.out.println(apples);

        // Method 3 - Using lambdas (Can be used where FI is expected)
        apples.sort((Apple a, Apple b) -> Integer.compare(a.getWeight(), b.getWeight()));
        System.out.println(apples);

        // Method 3a - Type inference to clean things up
        apples.sort((a, b) -> Integer.compare(a.getWeight(), b.getWeight()));
        System.out.println(apples);

        // Method 4 - Using the comparing method in Comparator to generate a comparator
        apples.sort(Comparator.comparing((a) -> a.getWeight()));

        // Use static import for comparator comparing
        apples.sort(comparing((a) -> a.getWeight()));

        // Use method references to convert lambda to MR.
        apples.sort(comparing(Apple::getWeight));
    }
}
