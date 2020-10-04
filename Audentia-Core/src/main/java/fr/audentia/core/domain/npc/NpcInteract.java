package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.bank.BankInventoryOpen;
import fr.audentia.core.domain.bank.BankNpcProvider;
import fr.audentia.core.domain.shop.ShopInventoryOpen;

import java.util.Optional;
import java.util.UUID;

public class NpcInteract {

    private final BankNpcProvider bankNpcProvider;
    private final BankInventoryOpen bankInventoryOpen;
    private final ShopInventoryOpen shopInventoryOpen;

    public NpcInteract(BankNpcProvider bankNpcProvider, BankInventoryOpen bankInventoryOpen, ShopInventoryOpen shopInventoryOpen) {
        this.bankNpcProvider = bankNpcProvider;
        this.bankInventoryOpen = bankInventoryOpen;
        this.shopInventoryOpen = shopInventoryOpen;
    }

    public void interactWithNpc(UUID playerUUID, String npcName) { // TODO : return message

        if (npcName.isEmpty()) {
            return;
        }

        if (Optional.of(npcName).equals(bankNpcProvider.getName())) {
            bankInventoryOpen.openInventory(playerUUID);
            return;
        }

        this.shopInventoryOpen.openShop(playerUUID, npcName);
    }

}
