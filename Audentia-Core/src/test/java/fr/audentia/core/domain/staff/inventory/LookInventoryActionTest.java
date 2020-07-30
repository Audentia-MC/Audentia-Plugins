package fr.audentia.core.domain.staff.inventory;

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
class LookInventoryActionTest {

    @Mock
    private PlayerInventoryOpener playerInventoryOpener;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private WorldPlayerFinder worldPlayerFinder;

    private LookInventoryAction lookInventoryAction;

    @BeforeEach
    void setUp() {
        lookInventoryAction = new LookInventoryAction(playerInventoryOpener, rolesRepository, worldPlayerFinder);
    }

    @Test
    @DisplayName("look inventory should open inventory of target when player is staff and target is in world")
    void lookInventory_shouldOpenInventoryOfTarget_whenPlayerIsStaffAndTargetIsInWorld() {

        when(rolesRepository.getRole(any())).thenReturn(new Role(1, true, true, 0));
        when(worldPlayerFinder.isInWorld((UUID) any())).thenReturn(true);

        String result = lookInventoryAction.lookInventory(UUID.randomUUID(), UUID.randomUUID());

        verify(playerInventoryOpener, times(1)).openInventory(any(), any());
        assertThat(result).isEqualTo("<success>Ouverture de l'inventaire.");
    }

    @Test
    @DisplayName("look inventory should do nothing when player isn't staff")
    void lookInventory_shouldDoNothing_whenPlayerIsNotStaff() {

        when(rolesRepository.getRole(any())).thenReturn(new Role(1, false, true, 0));

        String result = lookInventoryAction.lookInventory(UUID.randomUUID(), UUID.randomUUID());

        verifyNoInteractions(playerInventoryOpener);
        assertThat(result).isEqualTo("<error>Vous ne pouvez pas ouvrir l'inventaire de quelqu'un.");
    }

    @Test
    @DisplayName("look inventory should do nothing when target isn't in world")
    void lookInventory_shouldDoNothing_whenTargetIsNotInWorld() {

        when(rolesRepository.getRole(any())).thenReturn(new Role(1, true, true, 0));
        when(worldPlayerFinder.isInWorld((UUID) any())).thenReturn(false);

        String result = lookInventoryAction.lookInventory(UUID.randomUUID(), UUID.randomUUID());

        verifyNoInteractions(playerInventoryOpener);
        assertThat(result).isEqualTo("<error>Ce joueur n'est pas connecté.");
    }

}
