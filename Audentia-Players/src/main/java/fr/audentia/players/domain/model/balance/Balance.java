package fr.audentia.players.domain.model.balance;

public class Balance {

    private final int balance;

    public Balance(int balance) {
        this.balance = balance;
    }

    public boolean hasBalance() {
        return this.balance != -1;
    }

    public Balance add(int count) {
        return new Balance(balance + count);
    }

    @Override
    public String toString() {
        return String.valueOf(balance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balance balance1 = (Balance) o;
        return balance == balance1.balance;
    }

}
