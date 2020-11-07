package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.npc.NpcInteract;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

        String result = npcInteract.interactWithNpc(event.getPlayer().getUniqueId(), event.getRightClicked().getName());

        if (!result.isEmpty()) {
            event.getPlayer().sendMessage(ChatUtils.formatWithPrefix(result));
        }
    }

}
