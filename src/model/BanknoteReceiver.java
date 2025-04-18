package model;

import java.util.Scanner;

public class BanknoteReceiver implements MoneyReceiver {
    private int balance;

    public BanknoteReceiver(int balance) {
        this.balance = balance;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void addMoney(int amount) {
        balance += amount;
    }

    @Override
    public void deduct(int amount) {
        balance -= amount;
    }

    public void interactivelyAddMoney() {
        System.out.println("Введите сумму для пополнения:");
        int input = new Scanner(System.in).nextInt();
        addMoney(input);
        System.out.println("Баланс пополнен на " + input);
    }
}
