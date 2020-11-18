package fr.audentia.core.domain.staff.kick;

import fr.audentia.core.domain.staff.ban.LogsRepository;
import fr.audentia.players.domain.teams.RolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.util.UUID;

import static fr.audentia.players.domain.model.roles.RoleBuilder.aRole;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KickActionTest {

    @Mock
    private PlayerKicker playerKicker;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private LogsRepository logsRepository;

    private KickAction banAction;

    @BeforeEach
    void setUp() {
        banAction = new KickAction(playerKicker, rolesRepository, logsRepository);
    }

    @Test
    @DisplayName("ban should ban player and save ban when player is staff")
    void ban_shouldBanPlayer_whenPlayerIsStaff() {

        when(rolesRepository.getRole(any())).thenReturn(aRole()
                .withName("Admin")
                .withColor(Color.BLACK)
                .withEchelon(999)
                .build());

        String result = banAction.kick(UUID.randomUUID(), UUID.randomUUID());

        verify(playerKicker, times(1)).kick(any());
        assertThat(result).isEqualTo("<success>Joueur kick.");
    }

    @Test
    @DisplayName("ban should do nothing when player isn't high staff")
    void ban_shouldDoNothingWhenPlayerIsNotStaff() {

        when(rolesRepository.getRole(any())).thenReturn(aRole()
                .withName("Admin")
                .withColor(Color.BLACK)
                .withEchelon(999)
                .build());

        String result = banAction.kick(UUID.randomUUID(), UUID.randomUUID());

        verifyNoInteractions(playerKicker);
        assertThat(result).isEqualTo("<error>Vous ne pouvez pas kick de joueur.");
    }

}
