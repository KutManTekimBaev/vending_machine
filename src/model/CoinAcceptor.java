package model;

public class CoinAcceptor implements MoneyReceiver {
    private int balance;

    public CoinAcceptor(int balance) {
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
}
