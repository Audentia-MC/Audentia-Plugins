package fr.audentia.core.domain.bank;

import java.util.UUID;

public class BankInventoryInteract {

    private final InventoryUtilities inventoryUtilities;
    private final BankManager bankManager;

    public BankInventoryInteract(InventoryUtilities inventoryUtilities, BankManager balanceManage) {
        this.inventoryUtilities = inventoryUtilities;
        this.bankManager = balanceManage;
    }

    public String interact(UUID playerUUID, int count) {

        if (!inventoryUtilities.hasEmeralds(playerUUID, count)) {
            return "<error>Vous ne pouvez pas déposer ce nombre d'émeraudes.";
        }

        String result = bankManager.depositEmeralds(playerUUID, count);

        if (result.contains("<success>")) {
            inventoryUtilities.removeEmeralds(playerUUID, count);
        }

        return result;
    }

}
