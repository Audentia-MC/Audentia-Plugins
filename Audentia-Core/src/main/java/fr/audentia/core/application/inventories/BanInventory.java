package fr.audentia.core.application.inventories;

import fr.audentia.core.domain.staff.ban.BanAction;
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

public class BanInventory implements InventoryProvider {

    private final BanAction banAction;
    private final UUID targetUUID;

    public BanInventory(BanAction banAction, UUID targetUUID) {
        this.banAction = banAction;
        this.targetUUID = targetUUID;
    }

    public static void open(BanAction banAction, UUID targetUUID, Player player) {

        Player target = Bukkit.getPlayer(targetUUID);
        if (target == null) {
            return;
        }

        SmartInventory.builder()
                .id("ban")
                .title("Bannir : " + target.getName())
                .provider(new BanInventory(banAction, targetUUID))
                .closeable(true)
                .size(3, 9)
                .build()
                .open(player);
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        contents.set(new SlotPos(1, 3), ClickableItem.of(
                anItemStack()
                        .withName("Ban")
                        .withMaterial(Material.BEDROCK)
                        .withAmount(1)
                        .addLore("Confirmer")
                        .build(),
                event -> {
                    String result = banAction.ban(player.getUniqueId(), targetUUID);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                    if (result.startsWith("<success>")) player.closeInventory();
                }
        ));

        contents.set(new SlotPos(1, 5), ClickableItem.of(
                anItemStack()
                        .withName("Ban")
                        .withMaterial(Material.LEATHER)
                        .withAmount(1)
                        .addLore("Annuler")
                        .build(),
                event -> player.closeInventory()
        ));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

}
