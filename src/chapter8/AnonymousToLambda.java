package chapter8;

public class AnonymousToLambda {
    int a = 10;

    // Anonymous class
    Runnable r1 = new Runnable() {
        @Override
        public void run() {
            System.out.println("Hello");

            // this here refers to anonymous class itself - hence null
            System.out.println(this.getClass().getSimpleName());

            // Shadowing a variable in external scope - OK
            int a = 2;
            System.out.println(a);
        }
    };

    // Corresponding lambda
    Runnable r1Refactored = () -> {
        System.out.println("Hello");

        // this here refers to enclosing class - AnonymousToLambda
        System.out.println(this.getClass().getSimpleName());

        // Shadowing a variable in external scope - seems OK....
        int a = 5;
        System.out.println(a);
    };

    interface Task {
        public void execute();
    }

    public void doSomething(Runnable r) {r.run();}
    public void doSomething(Task t) {t.execute();}

    public static void main(String[] args) {
        AnonymousToLambda testClass = new AnonymousToLambda();
        testClass.r1.run();
        testClass.r1Refactored.run();

        // No ambiguity in invoking overloaded methods with anonymous classes
        testClass.doSomething(new Task() {
            @Override
            public void execute() {
                System.out.println("doSomething method overload - no ambiguity here");
            }
        });

        // Ambiguity in invoking overloaded methods with lambda expressions
        // Since the type of lambda depends upon context - both runnable and task are valid target types

        // testClass.doSomething(() -> System.out.println("doSomething method overload - ambiguity here"));

        // Explicit cast added to resolve ambiguity
        testClass.doSomething((Task)() -> System.out.println("doSomething method overload - NO ambiguity here"));

    }
}
