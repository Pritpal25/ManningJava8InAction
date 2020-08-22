package chapter2.BehaviorParameterization;

import java.util.Arrays;
import java.util.List;

public class PrettyPrintAppleLambdas {
    public static void prettyPrintApple(List<Apple> apples, PrettyPrintFormatter formatter) {
        for(Apple apple : apples) {
            System.out.println(formatter.format(apple));
        }
    }

    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple(100, "Red"), new Apple(150, "Green"), new Apple(75, "Red"));

        prettyPrintApple(list, (Apple a) -> "This is a simple formatter in lambda : " + a.getWeight());
        prettyPrintApple(list, (Apple a) -> "This is a fancy formatter in lambda : " + a.getColor());
    }
}
