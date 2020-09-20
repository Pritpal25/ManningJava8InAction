package chapter10.PriceFinderApplication;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Shop {
    private String shopName;

    public Shop(String name) {
        this.shopName = name;
    }

    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> future = new CompletableFuture<>();

        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                future.complete(price);
            } catch(Exception e) {
                future.completeExceptionally(e);
            }
        }).start();

        return future;
    }

    // This is an exact equivalent of the above getAsyncPrice method
    // Even includes the exceptions that are propagated to the client.
    public Future<Double> getPriceAsyncVersion2(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    private double calculatePrice(String product) {
        // Uncomment this to see the affect of how this exception goes into the cause
        // of ExecutionException in the client invoking getPrice()
        /*
        if ("Books".equals(product)) {
            throw new RuntimeException("Dont have this product");
        }
         */

        delay();
        return new Random().nextDouble()*product.charAt(0) + product.charAt(1);
    }

    private void delay() {
        try {
            Thread.sleep(900L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        Shop shop = new Shop("Best Shop");

        long start = System.nanoTime();
        Future<Double> future = shop.getPriceAsync("Books");
        long invocation_time = (System.nanoTime()-start)/1_000_000;
        System.out.println("Invocation time : " + invocation_time);

        // doOther work

        // Here the client can keep waiting indefinitely for the price if there is
        // some issue with price calculation and it doesn't return. So we will use a timeout.
        //double price = future.get();

        double price = 0;
        try {
            // Now if there is some issue, which lasts longer than timeout, we'll just get
            // TimeoutException. In order to get the original exception, we will update the
            // new Thread to include completeExceptionally(). This way client will receive
            // the original exception as the cause in ExecutionException
            price = future.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println(price);

        long retrievalTime = (System.nanoTime()-start)/1_000_000;
        System.out.println("Retrieval time : " + retrievalTime);
    }
}
