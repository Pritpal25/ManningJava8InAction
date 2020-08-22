package chapter2.BehaviorParameterization.Generic;

import chapter2.BehaviorParameterization.Apple;

import java.util.Arrays;
import java.util.List;

public class PrettyPrint {
    public static <T> void printList(List<T> list, PrettyFormatter<T> formatter) {
        for(T t : list) {
            System.out.println(formatter.format(t));
        }
    }

    public static void main(String[] args) {
        List<Apple> appleList = Arrays.asList(new Apple(10, "RED"), new Apple(20, "GREEN"));
        printList(appleList, (Apple a) -> "This is a lambda formatter in generic package : " + a.getColor() + " " + a.getWeight());

        List<Integer> integerList = Arrays.asList(2, 1, 4, 3, 5);
        printList(integerList, (Integer i) -> "This is a lambda formatter in generic package : " + i.toString());
    }
}
