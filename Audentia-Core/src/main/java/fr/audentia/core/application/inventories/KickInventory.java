package fr.audentia.core.application.inventories;

import fr.audentia.core.domain.staff.kick.KickAction;
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

public class KickInventory implements InventoryProvider {


    private final KickAction kickAction;
    private final UUID targetUUID;

    public KickInventory(KickAction kickAction, UUID targetUUID) {
        this.kickAction = kickAction;
        this.targetUUID = targetUUID;
    }

    public static void open(KickAction kickAction, UUID targetUUID, Player player) {

        Player target = Bukkit.getPlayer(targetUUID);
        if (target == null) {
            return;
        }

        SmartInventory.builder()
                .id("kick")
                .title("Kick : " + target.getName())
                .provider(new KickInventory(kickAction, targetUUID))
                .closeable(true)
                .size(3, 9)
                .build()
                .open(player);
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        contents.set(new SlotPos(1, 3), ClickableItem.of(
                anItemStack()
                        .withName("Kick")
                        .withMaterial(Material.DARK_OAK_DOOR)
                        .withAmount(1)
                        .addLore("Confirmer")
                        .build(),
                event -> {
                    String result = kickAction.kick(player.getUniqueId(), targetUUID);
                    player.sendMessage(ChatUtils.format(result));
                    if (result.startsWith("<success>")) player.closeInventory();
                }
        ));

        contents.set(new SlotPos(1, 5), ClickableItem.of(
                anItemStack()
                        .withName("Kick")
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
