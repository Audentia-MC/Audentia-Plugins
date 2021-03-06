package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.game.GameState;
import fr.audentia.core.domain.game.GameStateManage;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListenerPlayerInteractTest {

    @Mock
    private PlayerInteractEvent event;

    @Mock
    private GameStateManage gameStateManage;

    private ListenerPlayerInteract listenerPlayerInteract;

    @BeforeEach
    void setUp() {
        listenerPlayerInteract = new ListenerPlayerInteract(gameStateManage);
        doCallRealMethod().when(event).useInteractedBlock();
        doCallRealMethod().when(event).useItemInHand();
        lenient().doCallRealMethod().when(event).setUseInteractedBlock(any());
        lenient().doCallRealMethod().when(event).setUseItemInHand(any());
    }

    @Test
    @DisplayName("On interact interaction should be cancelled at pre")
    void onInteract_shouldBeCancelledAtPre() {

        when(gameStateManage.isPlaying()).thenReturn(false);

        listenerPlayerInteract.onPlayerInteract(event);

        verify(event).setCancelled(true);
        assertThat(event.useInteractedBlock()).isEqualTo(Event.Result.DENY);
        assertThat(event.useItemInHand()).isEqualTo(Event.Result.DENY);
    }

    @Test
    @DisplayName("On interact interaction should be cancelled at post")
    void onInteract_shouldBeCancelledAtPost() {

        when(this.gameStateManage.isPlaying()).thenReturn(false);

        listenerPlayerInteract.onPlayerInteract(event);

        verify(event).setCancelled(true);
        assertThat(event.useInteractedBlock()).isEqualTo(Event.Result.DENY);
        assertThat(event.useItemInHand()).isEqualTo(Event.Result.DENY);
    }

    @Test
    @DisplayName("On interact interaction should be not cancelled pendant game")
    void onInteract_shouldNotBeCancelledATPendant() {

        when(gameStateManage.isPlaying()).thenReturn(true);

        listenerPlayerInteract.onPlayerInteract(event);

        verify(event, never()).setCancelled(anyBoolean());
        assertThat(event.useInteractedBlock()).isNotEqualTo(Event.Result.DENY);
        assertThat(event.useItemInHand()).isNotEqualTo(Event.Result.DENY);
    }

}
