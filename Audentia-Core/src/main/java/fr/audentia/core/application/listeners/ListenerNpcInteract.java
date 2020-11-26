package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.npc.NpcInteract;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class ListenerNpcInteract implements Listener {

    private final NpcInteract npcInteract;

    public ListenerNpcInteract(NpcInteract npcInteract) {
        this.npcInteract = npcInteract;
    }

    @EventHandler
    public void onInteractWithNpc(PlayerInteractAtEntityEvent event) {

        if (event.getRightClicked().getType() != EntityType.VILLAGER) {
            return;
        }

        Player player = event.getPlayer();
        if (event.getRightClicked().getCustomName() == null) {
            event.setCancelled(true);
            return;
        }

        String result = npcInteract.interactWithNpc(player.getUniqueId(), event.getRightClicked().getCustomName());

        if (!result.isEmpty()) {
            player.sendMessage(ChatUtils.formatWithPrefix(result));
        }
    }

    @EventHandler
    public void onInteractWithMerchant(InventoryOpenEvent event) {

        if (event.getInventory().getType() == InventoryType.MERCHANT) {
            event.getPlayer().closeInventory();
            event.setCancelled(true);
        }

    }

}
