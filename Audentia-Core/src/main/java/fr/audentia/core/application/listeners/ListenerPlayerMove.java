package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.game.GameStateManage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerPlayerMove implements Listener {

    private final GameStateManage gameStateManage;

    public ListenerPlayerMove(GameStateManage gameStateManage) {
        this.gameStateManage = gameStateManage;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        event.setCancelled(!gameStateManage.isPlaying());

    }

}
