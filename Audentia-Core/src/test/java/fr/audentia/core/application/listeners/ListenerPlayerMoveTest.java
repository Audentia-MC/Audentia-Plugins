package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.game.GameState;
import fr.audentia.core.domain.game.GamesInfosRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ListenerPlayerMoveTest {

    @Mock
    private PlayerMoveEvent event;

    @Mock
    private GamesInfosRepository gamesInfosRepository;

    private ListenerPlayerMove listenerPlayerMove;

    @BeforeEach
    void setUp() {
        this.listenerPlayerMove = new ListenerPlayerMove(this.gamesInfosRepository);
        doCallRealMethod().when(this.event).isCancelled();
        lenient().doCallRealMethod().when(this.event).setCancelled(anyBoolean());
    }

    @Test
    @DisplayName("On player move mouvement should be cancelled")
    void on_move_mouvement_should_be_cancelled_when_party_has_not_started() {

        when(this.gamesInfosRepository.getGameState()).thenReturn(GameState.PRE);

        this.listenerPlayerMove.onPlayerMove(this.event);

        assert this.event.isCancelled();
    }

    @Test
    @DisplayName("On player move mouvement should be cancelled")
    void on_move_mouvement_should_be_cancelled_when_party_is_over() {

        when(this.gamesInfosRepository.getGameState()).thenReturn(GameState.POST);

        this.listenerPlayerMove.onPlayerMove(this.event);

        assert this.event.isCancelled();
    }

    @Test
    @DisplayName("On player move mouvement should be not cancelled")
    void on_move_mouvement_should_be_not_cancelled_when_party_is_started() {

        when(this.gamesInfosRepository.getGameState()).thenReturn(GameState.PENDANT);

        this.listenerPlayerMove.onPlayerMove(this.event);

        assert !this.event.isCancelled();
    }

}
