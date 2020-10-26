package fr.audentia.core.domain.shop;

import fr.audentia.core.domain.model.shop.Shop;

import java.util.UUID;

public class ShopInventoryOpen {

    private final ShopRepository shopRepository;
    private final ShopInventoryOpener shopInventoryOpener;

    public ShopInventoryOpen(ShopRepository shopRepository, ShopInventoryOpener shopInventoryOpener) {
        this.shopRepository = shopRepository;
        this.shopInventoryOpener = shopInventoryOpener;
    }

    public void openShop(UUID playerUUID, String npcName) {

        Shop shop = shopRepository.getByNpcName(npcName);

        if (shop == null) {
            return;
        }

        shopInventoryOpener.openInventory(playerUUID, shop);
    }

}
