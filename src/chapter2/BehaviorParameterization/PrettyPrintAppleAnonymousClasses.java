package chapter2.BehaviorParameterization;

import java.util.Arrays;
import java.util.List;

public class PrettyPrintAppleAnonymousClasses {
    public static void prettyPrintApple(List<Apple> apples, PrettyPrintFormatter formatter) {
        for(Apple apple : apples) {
            System.out.println(formatter.format(apple));
        }
    }

    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple(100, "Red"), new Apple(150, "Green"), new Apple(75, "Red"));

        prettyPrintApple(list, new PrettyPrintFormatter() {
            public String format(Apple apple) {
                return "This is simple formatter in anonymous class. " + apple.getWeight();
            }
        });
        prettyPrintApple(list, new PrettyPrintFormatter() {
            public String format(Apple apple) {
                return "This is fancy formatter in anonymous class. " + apple.getColor();
            }
        });
    }
}
