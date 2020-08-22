package chapter2.BehaviorParameterization;

import java.util.Arrays;
import java.util.List;

public class PrettyPrintApple {
    // Why does inner/nested class need to be static ?
    // Learn from here : https://stackoverflow.com/questions/13373779/non-static-class-cannot-be-referenced-from-a-static-context
    public static class SimpleFormatter implements PrettyPrintFormatter {
        @Override
        public String format(Apple apple) {
            return "This is a " + apple.getColor() + " apple.";
        }
    }

    public static class FancyFormatter implements PrettyPrintFormatter {

        @Override
        public String format(Apple apple) {
            return "This apple weighs " + apple.getWeight() + " gm and has " + apple.getColor() + " color.";
        }
    }

    public static void prettyPrintApple(List<Apple> apples, PrettyPrintFormatter formatter) {
        for(Apple apple : apples) {
            System.out.println(formatter.format(apple));
        }
    }

    public static void main(String[] args) {
        List<Apple> list = Arrays.asList(new Apple(100, "Red"), new Apple(150, "Green"), new Apple(75, "Red"));

        prettyPrintApple(list, new SimpleFormatter());
        prettyPrintApple(list, new FancyFormatter());
    }
}
