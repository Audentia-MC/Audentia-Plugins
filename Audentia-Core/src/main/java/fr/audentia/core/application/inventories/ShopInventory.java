package fr.audentia.core.application.inventories;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.model.shop.Shop;
import fr.audentia.core.domain.model.shop.ShopItem;
import fr.audentia.core.domain.shop.ShopItemBuyAction;
import fr.audentia.players.utils.ItemStackBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static fr.audentia.players.utils.ItemStackBuilder.anItemStack;

public class ShopInventory implements InventoryProvider {

    private final Shop shop;
    private final BalanceManage balanceManage;
    private final ShopItemBuyAction shopItemBuyAction;

    public ShopInventory(Shop shop, BalanceManage balanceManage, ShopItemBuyAction shopItemBuyAction) {
        this.shop = shop;
        this.balanceManage = balanceManage;
        this.shopItemBuyAction = shopItemBuyAction;
    }

    public static void open(UUID playerUUID, Shop shop, int page, BalanceManage balanceManage, ShopItemBuyAction shopItemBuyAction) {

        SmartInventory.builder()
                .id("shop")
                .title("Boutique - " + page + "/" + (shop.items.size() / 45 + 1))
                .provider(new ShopInventory(shop, balanceManage, shopItemBuyAction))
                .closeable(true)
                .size(6, 9)
                .build()
                .open(Bukkit.getPlayer(playerUUID), page);
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        Pagination pagination = contents.pagination();

        if (pagination.getPage() != 1) {

            contents.set(new SlotPos(5, 0), ClickableItem.of(ItemStackBuilder.anItemStack()
                            .withName("Retour")
                            .withMaterial(Material.STICK)
                            .addLore("Aller à la page " + (Math.max(1, pagination.getPage() - 1)))
                            .build(),
                    event -> {
                        if (pagination.getPage() != 1) {
                            open(player.getUniqueId(), this.shop, pagination.getPage() - 1, this.balanceManage, shopItemBuyAction);
                        }
                    }));

        }

        if (pagination.getPage() != getPageCount()) {

            contents.set(new SlotPos(5, 8), ClickableItem.of(ItemStackBuilder.anItemStack()
                            .withName("Suivant")
                            .withMaterial(Material.STICK)
                            .addLore("Aller à la page " + (Math.min(getPageCount(), pagination.getPage() + 1)))
                            .build(),
                    event -> {
                        if (pagination.getPage() != 10) {
                            open(player.getUniqueId(), this.shop, pagination.getPage() + 1, this.balanceManage, shopItemBuyAction);
                        }
                    }));

        }

        int page = contents.pagination().getPage() - 1;
        List<ShopItem> items = this.shop.items;
        items.sort(Comparator.comparing(item -> item.material));

        for (int i = page * 45; i < items.size(); i++) {

            if (i >= (page + 1) * 45) {
                break;
            }

            ShopItem item = items.get(i);
            Material material = Material.getMaterial(item.material);

            if (material == null) {
                continue;
            }

            contents.set(getSlotPos(i - page * 45), ClickableItem.of(ItemStackBuilder.anItemStack()
                    .withName(ChatColor.WHITE + item.material)
                    .withMaterial(material)
                    .addLore(ChatColor.AQUA + "Prix unitaire : " + ChatColor.YELLOW + item.price + " émeraudes")
                    .addLore(ChatColor.AQUA + "Prix dizaine : " + ChatColor.YELLOW + (10 * item.price) + " émeraudes")
                    .addLore(ChatColor.AQUA + "Prix demi-stack : " + ChatColor.YELLOW + (32 * item.price) + " émeraudes")
                    .addLore(ChatColor.AQUA + "Prix stack : " + ChatColor.YELLOW + (64 * item.price) + " émeraudes")
                    .build(),
                    event -> ItemShopInventory.open(player.getUniqueId(), shop, item, balanceManage, page + 1, shopItemBuyAction)));

        }


    }

    private int getPageCount() {
        return (shop.items.size() / 45) + 1;
    }

    private SlotPos getSlotPos(int index) {
        return new SlotPos(index / 9, index % 9);
    }

    @Override
    public void update(Player player, InventoryContents contents) {

        contents.set(new SlotPos(5, 4), ClickableItem.empty(
                anItemStack()
                        .withMaterial(Material.EMERALD)
                        .withName("Solde de votre équipe : " + balanceManage.getBalance(player.getUniqueId()))
                        .build())
        );

    }

}
