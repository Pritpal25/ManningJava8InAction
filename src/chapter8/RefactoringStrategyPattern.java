package chapter8;

class IsAllLowerCase implements RefactoringStrategyPattern.ValidationStrategy {
    @Override
    public boolean validate(String s) {
        return s.matches("[a-z]+");
    }
}

class IsNumeric implements RefactoringStrategyPattern.ValidationStrategy {
    @Override
    public boolean validate(String s) {
        return s.matches("\\d+");
    }
}

class Validator {
    private RefactoringStrategyPattern.ValidationStrategy validationStrategy;

    public Validator(RefactoringStrategyPattern.ValidationStrategy validationStrategy) {
        this.validationStrategy = validationStrategy;
    }

    boolean validate(String s) {
        return validationStrategy.validate(s);
    }
}

public class RefactoringStrategyPattern {

    interface ValidationStrategy {
        boolean validate(String s);
    }

    public static void main(String[] args) {
        Validator v1 = new Validator(new IsAllLowerCase());
        Validator v2 = new Validator(new IsNumeric());

        boolean b1 = v1.validate("aaaa");
        boolean b2 = v2.validate("1234");

        System.out.println(b1 + " " + b2);

        // Instead of having to create boilerplate implementations of the interface
        // and the interface itself, we can replace the constructor in Validator
        // with Predicate<String>, which has the exact signature as the validate() method
        // in interface. No need for interface or classes.
        Validator v3 = new Validator((s) -> s.matches("[a-z]+"));
        Validator v4 = new Validator((s) -> s.matches("\\d+"));

        boolean b3 = v3.validate("aaaa");
        boolean b4 = v4.validate("1234");

        System.out.println(b3 + " " + b4);
    }
}
