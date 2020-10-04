package fr.audentia.core.domain.shop;

import fr.audentia.core.domain.bank.InventoryUtilities;
import fr.audentia.core.domain.model.shop.ShopItem;

import java.util.UUID;

public class ShopItemBuyAction {

    private final InventoryUtilities inventoryUtilities;

    public ShopItemBuyAction(InventoryUtilities inventoryUtilities) {
        this.inventoryUtilities = inventoryUtilities;
    }

    public String buy(UUID playerUUID, ShopItem item, int count) {

        int price = (int) Math.max(item.price * count + 1, Math.round(item.price * count * 1.1));

        if (inventoryUtilities.hasEmeralds(playerUUID, price)) {
            return "<error>Vous n'avez pas assez d'émeraudes.";
        }

        inventoryUtilities.removeEmeralds(playerUUID, price);

        inventoryUtilities.addItem(playerUUID, item.material, count);
        return "<success>Achat réalisé avec succès.";
    }

    public String sell(UUID playerUUID, ShopItem item, int count) {

        int price = (int) Math.max(item.price * count + 1, Math.round(item.price * count * 1.1));

        if (!inventoryUtilities.hasItem(playerUUID, item.material, count)) {
            return "<error>Vous n'avez pas assez d'items dans votre inventaire.";
        }

        inventoryUtilities.removeItems(playerUUID, item.material, count);
        inventoryUtilities.addItem(playerUUID, "EMERALD", price);
        return "<success>Vente réalisée avec succès.";
    }

}
