package chapter8;

import sun.util.resources.cldr.so.CurrencyNames_so;

import java.util.function.Consumer;

class Customer {}

public class RefactoringTemplatePattern {
    abstract class OnlineBanking {
        public void processCustomer(String id) {
            Customer customer = getCustomerWithId(id);
            makeCustomerHappy(customer);
        }

        // Different subclasses can provide implementation of this method.
        protected abstract void makeCustomerHappy(Customer customer);

        private Customer getCustomerWithId(String id) {
            return new Customer();
        }
    }

    static class RefactoredOnlineBanking {
        public void processCustomer(String id, Consumer<Customer> consumer) {
            Customer customer = getCustomerWithId(id);
            consumer.accept(customer);
        }

        private Customer getCustomerWithId(String id) {
            return new Customer();
        }
    }

    public static void main(String[] args) {
        // There is abstract class(commented out) for template method in traditional form.

        // Refactoring it to take consumer instead.
        RefactoredOnlineBanking rob = new RefactoredOnlineBanking();
        rob.processCustomer("id", (Customer customer) -> System.out.println("This is to make you happy"));
    }
}
