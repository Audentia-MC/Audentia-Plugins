package fr.audentia.core.domain.bank;

import fr.audentia.core.domain.balance.BalanceManage;

import java.util.UUID;

public class BankInventoryInteract {

    private final InventoryUtilities inventoryUtilities;
    private final BalanceManage balanceManage;

    public BankInventoryInteract(InventoryUtilities inventoryUtilities, BalanceManage balanceManage) {
        this.inventoryUtilities = inventoryUtilities;
        this.balanceManage = balanceManage;
    }

    public String interact(UUID playerUUID, int count) {

        if (!inventoryUtilities.hasEmeralds(playerUUID, count)) {
            return "<error>Vous ne pouvez pas déposer ce nombre d'émeraudes.";
        }

        inventoryUtilities.removeEmeralds(playerUUID, count);
        return balanceManage.addToBalance(playerUUID, count);
    }

}
