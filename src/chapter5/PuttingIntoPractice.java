package chapter5;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class PuttingIntoPractice {

    public static void main(String[] args)
    {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        System.out.println(transactions.stream()
                                       .filter(t -> t.getYear() == 2011)
                                       .sorted(comparing(Transaction::getValue))
                                       .collect(toList()));

        System.out.println(transactions.stream()
                                        .map(Transaction::getTrader)
                                        .map(Trader::getCity)
                                        .distinct()
                                        .collect(toList()));

        transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList())
                .forEach(System.out::println);

        System.out.println(transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted()
                .reduce("", (a, b) -> a+b));

        System.out.println(transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .anyMatch(city -> city.equals("Milan")));

        transactions.stream()
                    .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                    .map(Transaction::getValue)
                    .forEach(System.out::println);

        transactions.stream()
                    .map(Transaction::getValue)
                    .reduce(Integer::max);

        transactions.stream()
                    .reduce((t1,t2) -> t1.getValue() < t2.getValue() ? t1 : t2);

        transactions.stream()
                    .min(comparing(Transaction::getValue));



    }
}
