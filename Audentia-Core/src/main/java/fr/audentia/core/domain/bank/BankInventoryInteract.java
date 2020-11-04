package fr.audentia.core.domain.bank;

import java.util.UUID;

public class BankInventoryInteract {

    private final InventoryUtilities inventoryUtilities;
    private final BankManage bankManage;

    public BankInventoryInteract(InventoryUtilities inventoryUtilities, BankManage balanceManage) {
        this.inventoryUtilities = inventoryUtilities;
        this.bankManage = balanceManage;
    }

    public String interact(UUID playerUUID, int count) {

        if (!inventoryUtilities.hasEmeralds(playerUUID, count)) {
            return "<error>Vous n'avez pas assez d'émeraudes dans votre inventaire.";
        }

        return bankManage.depositEmeralds(playerUUID, count);
    }

}
