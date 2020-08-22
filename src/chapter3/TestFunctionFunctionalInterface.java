package chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/*
This is the usage of Function FunctionalInterface.
This causes boxing of the int in the result.
Implementing another process as the version that doesn't require boxing, autoboxing.
 */
public class TestFunctionFunctionalInterface {
    public static List<Integer> process(List<String> list, Function<String, Integer> function) {
        List<Integer> result = new ArrayList<>();

        for(String str : list) {
            result.add(function.apply(str));
        }

        return result;
    }

    public static int process(String str, ToIntFunction<String> function) {
        return function.applyAsInt(str);
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("Kaku", "Maneet", "Deepa", "Gagan", "Harpreet bhabhi");
        System.out.println(process(list, (String s) -> s.length()));

        System.out.println(process("Kaku", (String s) -> s.length()));
    }
}
