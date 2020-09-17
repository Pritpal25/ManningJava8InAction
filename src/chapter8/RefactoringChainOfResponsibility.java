package chapter8;

import java.util.function.Function;
import java.util.function.UnaryOperator;

abstract class ProcessingObject<T> {
    private ProcessingObject<T> successor;

    public void setSuccessor(ProcessingObject<T> successor) {
        this.successor = successor;
    }

    public T handle(T input) {
        T result = handleInput(input);
        if (successor != null) {
            return successor.handle(result);
        }

        return result;
    }

    protected abstract T handleInput(T input);
}

class SpellChecker extends ProcessingObject<String> {
    @Override
    public String handleInput(String input) {
        return input.replaceAll("labda", "lambda");
    }
}

class AddHeader extends ProcessingObject<String> {
    @Override
    public String handleInput(String input) {
        return "Beginning chain of responsibility : " + input;
    }
}

public class RefactoringChainOfResponsibility {
    public static void main(String[] args) {
        ProcessingObject<String> addHeader = new AddHeader();
        ProcessingObject<String> spellChecker = new SpellChecker();
        addHeader.setSuccessor(spellChecker);

        System.out.println(addHeader.handle("Sample labda input"));

        // Refactored form
        UnaryOperator<String> addHeaderUnary = (String s) -> "Beginning chain of responsibility : " + s;
        UnaryOperator<String> spellCheckerUnary = (String s) -> s.replaceAll("labda", "lambda");
        Function<String, String> pipeline = addHeaderUnary.andThen(spellCheckerUnary);

        System.out.println(pipeline.apply("Sample labda input"));
    }
}
