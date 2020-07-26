package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.game.GameState;
import fr.audentia.core.domain.game.GamesInfosRepository;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenerPlayerInteract implements Listener {

    private final GamesInfosRepository gamesInfosRepository;

    public ListenerPlayerInteract(GamesInfosRepository gamesInfosRepository) {
        this.gamesInfosRepository = gamesInfosRepository;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if(this.gamesInfosRepository.getGameState() != GameState.PENDANT) {
            event.setUseItemInHand(Event.Result.DENY);
            event.setUseInteractedBlock(Event.Result.DENY);
            event.setCancelled(true);
        }

    }

}
