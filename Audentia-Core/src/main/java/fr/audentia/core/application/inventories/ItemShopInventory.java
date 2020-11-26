package fr.audentia.core.application.inventories;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.model.shop.Shop;
import fr.audentia.core.domain.model.shop.ShopItem;
import fr.audentia.core.domain.shop.ShopItemBuyAction;
import fr.audentia.players.utils.ChatUtils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

import static fr.audentia.players.utils.ItemStackBuilder.anItemStack;

public class ItemShopInventory implements InventoryProvider {

    private final Shop shop;
    private final BalanceManage balanceManage;
    private final int page;
    private final ShopItem item;
    private final ShopItemBuyAction shopItemBuyAction;

    public ItemShopInventory(Shop shop, BalanceManage balanceManage, int page, ShopItem item, ShopItemBuyAction shopItemBuyAction) {
        this.shop = shop;
        this.balanceManage = balanceManage;
        this.page = page;
        this.item = item;
        this.shopItemBuyAction = shopItemBuyAction;
    }

    public static void open(UUID playerUUID, Shop shop, ShopItem item, BalanceManage balanceManage, int page, ShopItemBuyAction shopItemBuyAction) {

        SmartInventory.builder()
                .id("item_shop")
                .title("Item")
                .provider(new ItemShopInventory(shop, balanceManage, page, item, shopItemBuyAction))
                .closeable(true)
                .size(5, 9)
                .build()
                .open(Bukkit.getPlayer(playerUUID));
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        contents.set(new SlotPos(4, 0), ClickableItem.of(
                anItemStack()
                        .withMaterial(Material.STICK)
                        .withName("Retour")
                        .addLore("Retourner à la boutique")
                        .build(),
                event -> ShopInventory.open(player.getUniqueId(), shop, page, balanceManage, shopItemBuyAction)
        ));

        contents.set(new SlotPos(1, 1), ClickableItem.of(
                anItemStack()
                        .withMaterial(Material.getMaterial(item.material))
                        .withAmount(1)
                        .addLore("Clic gauche : vendre, " + Math.floor(item.price) + " émeraudes")
                        .addLore("Clic droit : acheter, " + (Math.max(Math.floor(item.price) + 1, Math.round(item.price * 1.1))) + " émeraudes")
                        .build(),
                event -> {
                    if (event.isLeftClick()) {
                        String result = shopItemBuyAction.sell(player.getUniqueId(), item, 1);
                        player.sendMessage(ChatUtils.formatWithPrefix(result));
                        return;
                    }
                    String result = shopItemBuyAction.buy(player.getUniqueId(), item, 1);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(1, 3), ClickableItem.of(
                anItemStack()
                        .withMaterial(Material.getMaterial(item.material))
                        .withAmount(10)
                        .addLore("Clic gauche : vendre, " + (Math.floor(item.price * 10)) + " émeraudes")
                        .addLore("Clic droit : acheter, " + (Math.max(Math.floor(item.price) * 10 + 1, Math.round(item.price * 10 * 1.1))) + " émeraudes")
                        .build(),
                event -> {
                    if (event.isLeftClick()) {
                        String result = shopItemBuyAction.sell(player.getUniqueId(), item, 10);
                        player.sendMessage(ChatUtils.formatWithPrefix(result));
                        return;
                    }
                    String result = shopItemBuyAction.buy(player.getUniqueId(), item, 10);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(1, 5), ClickableItem.of(
                anItemStack()
                        .withMaterial(Material.getMaterial(item.material))
                        .withAmount(32)
                        .addLore("Clic gauche : vendre, " + (Math.floor(item.price * 32)) + " émeraudes")
                        .addLore("Clic droit : acheter, " + (Math.max(Math.floor(item.price) * 32 + 1, Math.round(item.price * 32 * 1.1))) + " émeraudes")
                        .build(),
                event -> {
                    if (event.isLeftClick()) {
                        String result = shopItemBuyAction.sell(player.getUniqueId(), item, 32);
                        player.sendMessage(ChatUtils.formatWithPrefix(result));
                        return;
                    }
                    String result = shopItemBuyAction.buy(player.getUniqueId(), item, 32);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(1, 7), ClickableItem.of(
                anItemStack()
                        .withMaterial(Material.getMaterial(item.material))
                        .withAmount(64)
                        .addLore("Clic gauche : vendre, " + (Math.floor(item.price * 64)) + " émeraudes")
                        .addLore("Clic droit : acheter, " + (Math.max(Math.floor(item.price) * 64 + 1, Math.round(item.price * 64 * 1.1))) + " émeraudes")
                        .build(),
                event -> {
                    if (event.isLeftClick()) {
                        String result = shopItemBuyAction.sell(player.getUniqueId(), item, 64);
                        player.sendMessage(ChatUtils.formatWithPrefix(result));
                        return;
                    }
                    String result = shopItemBuyAction.buy(player.getUniqueId(), item, 64);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

        contents.set(new SlotPos(4, 8), ClickableItem.empty(
                anItemStack()
                        .withMaterial(Material.EMERALD)
                        .withName("Solde de votre équipe : " + balanceManage.getBalance(player.getUniqueId()))
                        .build())
        );

    }

}
