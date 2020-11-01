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
    private final ShopInventoryOpen netherShopInventoryOpen;
    private final NetherNpcRepository netherNpcRepository;

    public NpcInteract(BankNpcProvider bankNpcProvider, BankInventoryOpen bankInventoryOpen, ShopInventoryOpen shopInventoryOpen, ShopInventoryOpen netherShopInventoryOpen, NetherNpcRepository netherNpcRepository) {
        this.bankNpcProvider = bankNpcProvider;
        this.bankInventoryOpen = bankInventoryOpen;
        this.shopInventoryOpen = shopInventoryOpen;
        this.netherShopInventoryOpen = netherShopInventoryOpen;
        this.netherNpcRepository = netherNpcRepository;
    }

    public String interactWithNpc(UUID playerUUID, String npcName) {

        if (npcName.isEmpty()) {
            return "";
        }

        if (Optional.of(npcName).equals(bankNpcProvider.getName())) {
            return bankInventoryOpen.openInventory(playerUUID);
        }

        String netherNpcName = netherNpcRepository.getNetherNpcName();
        if (netherNpcName != null && netherNpcName.equals(npcName)) {
            return netherShopInventoryOpen.openShop(playerUUID, npcName);
        }

        return this.shopInventoryOpen.openShop(playerUUID, npcName);
    }

}
