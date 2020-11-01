package fr.audentia.core.application.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotPos;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
                .size(5, 9)
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

        for (int i = 0; i < 9; i++) {
            contents.set(new SlotPos(3, i), ClickableItem.empty(targetInventory.getItem(i)));
        }

        for (int i = 9; i < 36; i++) {
            contents.set(new SlotPos((i - 9) / 9, i % 9), ClickableItem.empty(targetInventory.getItem(i)));
        }

        contents.set(new SlotPos(4, 2), ClickableItem.empty(targetInventory.getItem(36)));
        contents.set(new SlotPos(4, 3), ClickableItem.empty(targetInventory.getItem(37)));
        contents.set(new SlotPos(4, 4), ClickableItem.empty(targetInventory.getItem(38)));
        contents.set(new SlotPos(4, 5), ClickableItem.empty(targetInventory.getItem(39)));

        contents.set(new SlotPos(4, 6), ClickableItem.empty(targetInventory.getItem(40)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

}
