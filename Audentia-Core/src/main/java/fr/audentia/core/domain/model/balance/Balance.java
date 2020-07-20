package fr.audentia.core.domain.model.balance;

public class Balance {

    private final int balance;

    public Balance(int balance) {
        this.balance = balance;
    }

    public boolean hasBalance() {
        return this.balance != -1;
    }

    @Override
    public String toString() {
        return String.valueOf(balance);
    }
}
