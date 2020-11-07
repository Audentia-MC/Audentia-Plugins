package fr.audentia.core.application.inventories;

import fr.audentia.core.domain.bank.BankInventoryInteract;
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

public class BankInventory implements InventoryProvider {

    private final BankInventoryInteract bankInventoryInteract;

    public BankInventory(BankInventoryInteract bankInventoryInteract) {
        this.bankInventoryInteract = bankInventoryInteract;
    }

    public static void open(UUID playerUUID, BankInventoryInteract bankInventoryInteract) {

        SmartInventory.builder()
                .id("bank")
                .title("Banque")
                .provider(new BankInventory(bankInventoryInteract))
                .closeable(true)
                .size(3, 9)
                .build()
                .open(Bukkit.getPlayer(playerUUID));
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        contents.set(new SlotPos(1, 1), ClickableItem.of(
                anItemStack()
                        .withMaterial(Material.EMERALD)
                        .withAmount(1)
                        .build(),
                event -> {
                    String result = bankInventoryInteract.interact(player.getUniqueId(), 1);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(1, 3), ClickableItem.of(
                anItemStack()
                        .withMaterial(Material.EMERALD)
                        .withAmount(10)
                        .build(),
                event -> {
                    String result = bankInventoryInteract.interact(player.getUniqueId(), 10);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(1, 5), ClickableItem.of(
                anItemStack()
                        .withMaterial(Material.EMERALD)
                        .withAmount(32)
                        .build(),
                event -> {
                    String result = bankInventoryInteract.interact(player.getUniqueId(), 32);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

        contents.set(new SlotPos(1, 7), ClickableItem.of(
                anItemStack()
                        .withMaterial(Material.EMERALD)
                        .withAmount(64)
                        .build(),
                event -> {
                    String result = bankInventoryInteract.interact(player.getUniqueId(), 64);
                    player.sendMessage(ChatUtils.formatWithPrefix(result));
                }
        ));

    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

}
