package fr.audentia.core.domain.balance;

import fr.audentia.core.domain.model.balance.BankTransfer;

import java.awt.Color;

public interface TransfersRepository {

    void registerTransfer(Color teamColor, BankTransfer bankTransfer);

}
