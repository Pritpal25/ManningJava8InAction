package chapter8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RefactoringObserverPattern {
    interface Observer {
        public void notifyObserver(String tweet);
    }

    static class NYTObserver implements  Observer {
        @Override
        public void notifyObserver(String tweet) {
            if(tweet != null && tweet.contains("trump")) {
                System.out.println("New Trump Headlines : " + tweet);
            }
        }
    }

    static class Guardian implements Observer {
        @Override
        public void notifyObserver(String tweet) {
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("New Queen Headlines : " + tweet);
            }
        }
    }

    interface Subject {
        void registerObservers(List<Observer> observers);
        void notifyObserver(String tweet);
    }

    static class Feed implements Subject {
        List<Observer> observers = new ArrayList<>();

        @Override
        public void registerObservers(List<Observer> observers) {
            observers.forEach(this.observers::add);
        }

        @Override
        public void notifyObserver(String tweet) {
            this.observers.forEach(o -> o.notifyObserver(tweet));
        }
    }

    public static void main(String[] args) {
        Feed feed = new Feed();

        // These specific implementations are nothing but string consumers.
        feed.registerObservers(Arrays.asList(new NYTObserver(), new Guardian()));

        feed.notifyObserver("queen like cheese sandwich.");

        //Could be refactored like below - but this is not ideal, since it doesn't define the
        // purpose and also if the logic becomes complex, then it reduces re-usability.
        feed.registerObservers(Arrays.asList((String tweet) -> {
            if(tweet != null && tweet.contains("trump")) {
                System.out.println("New Trump Headlines : " + tweet);
            }
        }));
    }
}
