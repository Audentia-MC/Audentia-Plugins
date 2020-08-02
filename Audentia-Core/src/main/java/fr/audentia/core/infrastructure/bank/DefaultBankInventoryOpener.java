package fr.audentia.core.infrastructure.bank;

import fr.audentia.core.application.inventories.BankInventory;
import fr.audentia.core.domain.bank.BankInventoryInteract;
import fr.audentia.core.domain.bank.BankInventoryOpener;

import java.awt.*;
import java.util.UUID;

public class DefaultBankInventoryOpener implements BankInventoryOpener {

    private final BankInventoryInteract bankInventoryInteract;

    public DefaultBankInventoryOpener(BankInventoryInteract bankInventoryInteract) {
        this.bankInventoryInteract = bankInventoryInteract;
    }

    @Override
    public void open(UUID playerUUID, Color teamColor) {
        BankInventory.open(playerUUID, bankInventoryInteract);
    }

}
