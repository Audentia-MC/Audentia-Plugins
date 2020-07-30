package fr.audentia.core.domain.staff.ban;

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
class BanActionTest {

    @Mock
    private PlayerBanner playerBanner;

    @Mock
    private BanRepository banRepository;

    @Mock
    private RolesRepository rolesRepository;

    private BanAction banAction;

    @BeforeEach
    void setUp() {
        banAction = new BanAction(playerBanner, banRepository, rolesRepository);
    }

    @Test
    @DisplayName("ban should ban player and save ban when player is staff")
    void ban_shouldBanPlayer_whenPlayerIsStaff() {

        when(rolesRepository.getRole(any())).thenReturn(new Role(1, true, false, 0));

        String result = banAction.ban(UUID.randomUUID(), UUID.randomUUID());

        verify(playerBanner, times(1)).ban(any());
        verify(banRepository, times(1)).ban(any(), any());
        assertThat(result).isEqualTo("<success>Joueur banni.");
    }

    @Test
    @DisplayName("ban should do nothing when player isn't staff")
    void ban_shouldDoNothingWhenPlayerIsNotStaff() {

        when(rolesRepository.getRole(any())).thenReturn(new Role(1, false, false, 0));

        String result = banAction.ban(UUID.randomUUID(), UUID.randomUUID());

        verifyNoInteractions(playerBanner);
        verifyNoInteractions(banRepository);
        assertThat(result).isEqualTo("<error>Vous ne pouvez pas bannir de joueur.");
    }

}
