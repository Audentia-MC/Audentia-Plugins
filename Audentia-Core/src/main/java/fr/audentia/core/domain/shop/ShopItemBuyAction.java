package fr.audentia.core.domain.shop;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.bank.BankManage;
import fr.audentia.core.domain.bank.InventoryUtilities;
import fr.audentia.core.domain.model.shop.ShopItem;

import java.util.UUID;

public class ShopItemBuyAction {

    private final InventoryUtilities inventoryUtilities;
    private final BankManage bankManage;
    private final BalanceManage balanceManage;

    public ShopItemBuyAction(InventoryUtilities inventoryUtilities, BankManage bankManage, BalanceManage balanceManage) {
        this.inventoryUtilities = inventoryUtilities;
        this.bankManage = bankManage;
        this.balanceManage = balanceManage;
    }

    public String buy(UUID playerUUID, ShopItem item, int count) {

        int price = (int) Math.max(item.price * count + 1, Math.round(item.price * count * 1.1));

        if (Integer.parseInt(balanceManage.getBalance(playerUUID)) < price) { // TODO: maybe look in inventory rather than in bank ?
            return "<error>Vous n'avez pas assez d'émeraudes.";
        }

        String result = bankManage.removeEmeralds(playerUUID, price);

        if (result.startsWith("<error>")) {
            return result;
        }

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
