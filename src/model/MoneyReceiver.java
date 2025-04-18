package model;

public interface MoneyReceiver {
    int getBalance();
    void addMoney(int amount);
    void deduct(int amount);
}
