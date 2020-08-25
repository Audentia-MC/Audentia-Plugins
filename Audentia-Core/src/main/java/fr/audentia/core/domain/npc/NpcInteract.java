package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.bank.BankInventoryOpen;
import fr.audentia.core.domain.bank.BankNpcProvider;

import java.util.Optional;
import java.util.UUID;

public class NpcInteract {

    private final BankNpcProvider bankNpcProvider;
    private final BankInventoryOpen bankInventoryOpen;

    public NpcInteract(BankNpcProvider bankNpcProvider, BankInventoryOpen bankInventoryOpen) {
        this.bankNpcProvider = bankNpcProvider;
        this.bankInventoryOpen = bankInventoryOpen;
    }

    public void interactWithNpc(UUID playerUUID, String npcName) { // TODO : return message

        if (npcName.isEmpty()) {
            return;
        }

        if (Optional.of(npcName).equals(bankNpcProvider.getName())) {
            bankInventoryOpen.openInventory(playerUUID);
            return;
        }

        // TODO : add interactions with an other npc
    }

}
