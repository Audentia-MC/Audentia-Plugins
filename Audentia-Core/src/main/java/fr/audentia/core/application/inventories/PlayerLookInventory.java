package fr.audentia.core.application.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class PlayerLookInventory implements InventoryProvider {

    private final UUID targetUUID;

    public PlayerLookInventory(UUID targetUUID) {
        this.targetUUID = targetUUID;
    }

    public static void openInventory(UUID staffUUID, UUID targetUUID) {
        Player target = Bukkit.getPlayer(targetUUID);
        if (target == null) {
            return;
        }

        SmartInventory.builder()
                .id("lookUp")
                .title("Inventaire de " + target.getName())
                .provider(new PlayerLookInventory(targetUUID))
                .closeable(true)
                .size(6, 9)
                .build()
                .open(Bukkit.getPlayer(staffUUID));
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        Player target = Bukkit.getPlayer(targetUUID);
        if (target == null) {
            return;
        }

        Inventory targetInventory = target.getInventory();

        for (int i = 0; i < 27; i++) {
            contents.set(new SlotPos(i / 9, i % 9), ClickableItem.empty(targetInventory.getItem(i + 9)));
        }

        for (int i = 0; i < 9; i++) {
            contents.set(new SlotPos(3, i), ClickableItem.empty(targetInventory.getItem(i + 36)));
        }

        contents.set(new SlotPos(5, 1), ClickableItem.empty(targetInventory.getItem(5)));
        contents.set(new SlotPos(5, 2), ClickableItem.empty(targetInventory.getItem(6)));
        contents.set(new SlotPos(5, 3), ClickableItem.empty(targetInventory.getItem(7)));
        contents.set(new SlotPos(5, 4), ClickableItem.empty(targetInventory.getItem(8)));

        contents.set(new SlotPos(5, 6), ClickableItem.empty(targetInventory.getItem(45)));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

}
