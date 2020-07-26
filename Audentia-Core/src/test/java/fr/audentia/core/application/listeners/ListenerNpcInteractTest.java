package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.npc.NpcInteract;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListenerNpcInteractTest {

    @Mock
    private PlayerInteractAtEntityEvent event;

    @Mock
    private Entity entity;

    @Mock
    private Player player;

    @Mock
    private NpcInteract npcInteract;

    private ListenerNpcInteract listenerNpcInteract;

    @BeforeEach
    void setUp() {
        listenerNpcInteract = new ListenerNpcInteract(npcInteract);
    }

    @Test
    @DisplayName("interact with an npc should trigger a call to npcInteract")
    void onInteractWithNpc_shouldCallNpcInteract_whenInteractWithNpc() {

        when(event.getPlayer()).thenReturn(player);
        when(player.getUniqueId()).thenReturn(UUID.randomUUID());
        when(event.getRightClicked()).thenReturn(entity);
        when(entity.getType()).thenReturn(EntityType.VILLAGER);
        when(entity.getName()).thenReturn("Tony");

        listenerNpcInteract.onInteractWithNpc(event);

        verify(npcInteract, times(1)).interactWithNpc(any(), eq("Tony"));
    }


    @Test
    @DisplayName("interact with a zombie should trigger nothing")
    void onInteractWithNpc_shouldDoNothing_whenInteractWithZombie() {

        when(event.getRightClicked()).thenReturn(entity);
        when(event.getRightClicked().getType()).thenReturn(EntityType.ZOMBIE);

        listenerNpcInteract.onInteractWithNpc(event);

        verifyNoInteractions(npcInteract);
    }

}
