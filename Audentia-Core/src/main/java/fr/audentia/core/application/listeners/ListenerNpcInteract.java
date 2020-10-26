package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.npc.NpcInteract;
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

        npcInteract.interactWithNpc(event.getPlayer().getUniqueId(), event.getRightClicked().getName());
    }

}
