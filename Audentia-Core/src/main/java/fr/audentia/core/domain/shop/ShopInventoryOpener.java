package fr.audentia.core.domain.shop;

import fr.audentia.core.domain.model.shop.Shop;

import java.util.UUID;

public interface ShopInventoryOpener {

    void openInventory(UUID playerUUID, Shop shop);

}
