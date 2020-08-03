package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.game.GameState;
import fr.audentia.core.domain.game.GamesInfosRepository;
import org.bukkit.event.player.PlayerMoveEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
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
    void onMove_shouldBeCancelled_whenPartyHasNotStarted() {

        when(this.gamesInfosRepository.getGameState()).thenReturn(GameState.WAITING);

        this.listenerPlayerMove.onPlayerMove(this.event);

        assertThat(this.event.isCancelled()).isTrue();
    }

    @Test
    @DisplayName("On player move mouvement should be cancelled")
    void onMove_shouldBeCancelled_whenPartyIsOver() {

        when(this.gamesInfosRepository.getGameState()).thenReturn(GameState.PAUSED);

        this.listenerPlayerMove.onPlayerMove(this.event);

        assertThat(this.event.isCancelled()).isTrue();
    }

    @Test
    @DisplayName("On player move mouvement should be not cancelled")
    void onMove_shouldNotBeCancelled_whenPartyIsStarted() {

        when(this.gamesInfosRepository.getGameState()).thenReturn(GameState.PLAYING);

        this.listenerPlayerMove.onPlayerMove(this.event);

        assertThat(this.event.isCancelled()).isFalse();
    }

}
