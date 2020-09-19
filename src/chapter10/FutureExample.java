package chapter10;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureExample {
    public static void main(String[] args)
    {
        // ExecutorService is the one that executes the threads
        ExecutorService service = Executors.newCachedThreadPool();

        // Service takes in instances of Callable Objects and returns Future<T> objects
        Future<Double> future = service.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                System.out.println("This is  another thread.");
                return new Double("1234.56");
            }
        });
        
        System.out.println("Doing task in main thread");

        try {
            // Future::get method returns the output of computation, its always good to use
            // the overloaded version of the get method to ensure the amount of time the main
            // thread stays blocked on get method.
            System.out.println(future.get(1, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
