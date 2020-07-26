package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.game.GameState;
import fr.audentia.core.domain.game.GamesInfosRepository;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerPlayerMove implements Listener {

    private final GamesInfosRepository gamesInfosRepository;

    public ListenerPlayerMove(GamesInfosRepository gamesInfosRepository) {
        this.gamesInfosRepository = gamesInfosRepository;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if(!(this.gamesInfosRepository.getGameState() == GameState.PENDANT)) {
            event.setCancelled(true);
        }

    }

}
