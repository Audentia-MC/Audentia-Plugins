package fr.audentia.core.domain.staff.teleport;

import fr.audentia.core.domain.staff.WorldPlayerFinder;
import fr.audentia.players.domain.model.Role;
import fr.audentia.players.domain.teams.RolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeleportActionTest {

    @Mock
    private PlayerTeleporter playerTeleporter;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private WorldPlayerFinder worldPlayerFinder;

    private TeleportAction teleportAction;

    @BeforeEach
    void setUp() {
        teleportAction = new TeleportAction(playerTeleporter, rolesRepository, worldPlayerFinder);
    }

    @Test
    @DisplayName("teleport should teleport player when player is staff and target is in world")
    void teleport_shouldTeleportPlayer_whenPlayerIsStaffAndTargetIsInWorld() {

        when(rolesRepository.getRole(any())).thenReturn(new Role(1, true, true, 0));
        when(worldPlayerFinder.isInWorld((UUID) any())).thenReturn(true);

        String result = teleportAction.teleport(UUID.randomUUID(), UUID.randomUUID());

        verify(playerTeleporter, times(1)).teleport(any(), any());
        assertThat(result).isEqualTo("<success>Téléportation réussie.");
    }

    @Test
    @DisplayName("teleport should do nothing when player isn't staff")
    void teleport_shouldDoNothing_whenPlayerIsNotStaff() {

        when(rolesRepository.getRole(any())).thenReturn(new Role(1, false, true, 0));

        String result = teleportAction.teleport(UUID.randomUUID(), UUID.randomUUID());

        verifyNoInteractions(playerTeleporter);
        assertThat(result).isEqualTo("<error>Vous ne pouvez pas vous tp à quelqu'un.");
    }


    @Test
    @DisplayName("teleport should do nothing when target isn't in world")
    void teleport_shouldDoNothing_whenTargetIsNotInWorld() {

        when(rolesRepository.getRole(any())).thenReturn(new Role(1, true, true, 0));
        when(worldPlayerFinder.isInWorld((UUID) any())).thenReturn(false);

        String result = teleportAction.teleport(UUID.randomUUID(), UUID.randomUUID());

        verifyNoInteractions(playerTeleporter);
        assertThat(result).isEqualTo("<error>Ce joueur n'est pas connecté.");
    }

}
