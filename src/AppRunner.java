import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final MoneyReceiver moneyReceiver;


    private static boolean isExit = false;

    public AppRunner(MoneyReceiver moneyReceiver) {
        this.moneyReceiver = moneyReceiver;
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
    }

    public static void run() {
        System.out.println("Выберите способ оплаты:");
        System.out.println("1 - Монеты");
        System.out.println("2 - Купюры");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        MoneyReceiver receiver;
        if ("1".equals(choice)) {
            receiver = new CoinAcceptor(100);
        } else if ("2".equals(choice)) {
            receiver = new BanknoteReceiver(100);
        } else {
            System.out.println("Неверный выбор");
            return;
        }
        AppRunner app = new AppRunner(receiver);
        while (!isExit) {
            app.startSimulation();
        }
    }
    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Баланс: " + moneyReceiver.getBalance());

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);

    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (moneyReceiver.getBalance() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);

        if ("a".equalsIgnoreCase(action)) {
            if (moneyReceiver instanceof BanknoteReceiver banknoteReceiver) {
                banknoteReceiver.interactivelyAddMoney();
            } else {
                moneyReceiver.addMoney(10);
                print("Вы пополнили баланс на 10");
            }
            return;
        }

        if ("h".equalsIgnoreCase(action)) {
            isExit = true;
            return;
        }

        try {
            ActionLetter letter = ActionLetter.valueOf(action.toUpperCase());
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(letter)) {
                    if (moneyReceiver.getBalance() >= products.get(i).getPrice()) {
                        moneyReceiver.deduct(products.get(i).getPrice());
                        print("Вы купили " + products.get(i).getName());
                    } else {
                        print("Недостаточно средств для покупки.");
                    }
                    return;
                }
            }
            print("Товар с такой буквой не найден.");
        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попробуйте ещё раз.");
        }
    }


    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
