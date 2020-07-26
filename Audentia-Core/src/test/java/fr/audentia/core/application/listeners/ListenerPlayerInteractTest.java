package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.game.GameState;
import fr.audentia.core.domain.game.GamesInfosRepository;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListenerPlayerInteractTest {

    @Mock
    private PlayerInteractEvent event;

    @Mock
    private GamesInfosRepository gamesInfosRepository;

    private ListenerPlayerInteract listenerPlayerInteract;

    @BeforeEach
    void setUp() {
        this.listenerPlayerInteract = new ListenerPlayerInteract(this.gamesInfosRepository);
        doCallRealMethod().when(this.event).useInteractedBlock();
        doCallRealMethod().when(this.event).useItemInHand();
        lenient().doCallRealMethod().when(this.event).setUseInteractedBlock(any());
        lenient().doCallRealMethod().when(this.event).setUseItemInHand(any());
    }

    @Test
    @DisplayName("On interact interaction should be cancelled at pre")
    void on_interact_interaction_should_be_cancelled_at_pre() {
        when(this.gamesInfosRepository.getGameState()).thenReturn(GameState.PRE);
        listenerPlayerInteract.onPlayerInteract(this.event);

        assert this.event.useInteractedBlock() == Event.Result.DENY;
        assert this.event.useItemInHand() == Event.Result.DENY;
    }

    @Test
    @DisplayName("On interact interaction should be cancelled at post")
    void on_interact_interaction_should_be_cancelled_at_post() {
        when(this.gamesInfosRepository.getGameState()).thenReturn(GameState.POST);
        listenerPlayerInteract.onPlayerInteract(this.event);

        assert this.event.useInteractedBlock() == Event.Result.DENY;
        assert this.event.useItemInHand() == Event.Result.DENY;
    }

    @Test
    @DisplayName("On interact interaction should be not cancelled pendant game")
    void on_interact_interaction_should_be_cancelled_pendant_game() {
        when(this.gamesInfosRepository.getGameState()).thenReturn(GameState.PENDANT);
        listenerPlayerInteract.onPlayerInteract(this.event);

        assert this.event.useInteractedBlock() != Event.Result.DENY;
        assert this.event.useItemInHand() != Event.Result.DENY;
    }

}
