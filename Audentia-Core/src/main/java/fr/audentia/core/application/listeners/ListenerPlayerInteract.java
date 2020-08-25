package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.game.GameStateManage;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerPlayerInteract implements Listener {

    private final GameStateManage gameStateManage;

    public ListenerPlayerInteract(GameStateManage gameStateManage) {
        this.gameStateManage = gameStateManage;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (this.gameStateManage.isPlaying()) {
            return;
        }

        event.setUseItemInHand(Event.Result.DENY);
        event.setUseInteractedBlock(Event.Result.DENY);
        event.setCancelled(true);
    }

}
