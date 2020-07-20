package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.balance.BalanceManage;

import java.util.UUID;

public class BankInventoryInteract {

    private final InventoryChecker inventoryChecker;
    private final BalanceManage balanceManage;

    public BankInventoryInteract(InventoryChecker inventoryChecker, BalanceManage balanceManage) {
        this.inventoryChecker = inventoryChecker;
        this.balanceManage = balanceManage;
    }

    public String interact(UUID playerUUID, int count) {

        if (!inventoryChecker.hasEmeralds(playerUUID, count)) {
            return "<error>Vous ne pouvez pas déposer ce nombre d'émeraudes.";
        }

        return balanceManage.addToBalance(playerUUID, count);
    }

}
