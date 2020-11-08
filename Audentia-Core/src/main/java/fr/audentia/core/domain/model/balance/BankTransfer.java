package fr.audentia.core.domain.model.balance;

import java.time.LocalDateTime;

public class BankTransfer {

    public final LocalDateTime date;
    public final int amount;

    public BankTransfer(LocalDateTime date, int amount) {
        this.date = date;
        this.amount = amount;
    }

}
