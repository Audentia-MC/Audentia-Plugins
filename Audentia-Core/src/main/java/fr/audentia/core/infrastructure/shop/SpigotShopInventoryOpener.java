package fr.audentia.core.infrastructure.shop;

import fr.audentia.core.application.inventories.ShopInventory;
import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.model.shop.Shop;
import fr.audentia.core.domain.shop.ShopInventoryOpener;
import fr.audentia.core.domain.shop.ShopItemBuyAction;

import java.util.UUID;

public class SpigotShopInventoryOpener implements ShopInventoryOpener {

    private final ShopItemBuyAction shopItemBuyAction;
    private final BalanceManage balanceManage;

    public SpigotShopInventoryOpener(ShopItemBuyAction shopItemBuyAction, BalanceManage balanceManage) {
        this.shopItemBuyAction = shopItemBuyAction;
        this.balanceManage = balanceManage;
    }

    @Override
    public void openInventory(UUID playerUUID, Shop shop) {
        ShopInventory.open(playerUUID, shop,  1, balanceManage, shopItemBuyAction);
    }

}
