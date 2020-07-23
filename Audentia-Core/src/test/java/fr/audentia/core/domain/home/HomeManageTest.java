package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.HomeLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HomeManageTest {

    @Mock
    private PlayerTeleport playerTeleporter;

    @Mock
    private HomeRepository homeRepository;

    private HomeManage homeManage;

    @BeforeEach
    void setUp() {
        homeManage = new HomeManage(homeRepository, playerTeleporter);
    }

    @Test
    @DisplayName("home 1 should tp player to home n°1 when it is set up")
    void home_shouldTpPlayerToHisHome_whenHomeIsSetUp() {

        HomeLocation homeLocation = new HomeLocation(0, 0, 0);
        when(homeRepository.getHome(any(), anyInt())).thenReturn(Optional.of(homeLocation));

        String result = homeManage.teleportToHome(UUID.randomUUID());

        verify(playerTeleporter, times(1)).teleport(any(), eq(homeLocation));
        assertThat(result).isEqualTo("<success>Téléportation réussie.");
    }

    @Test
    @DisplayName("home 1 should do nothing when home isn't set up")
    void home_shouldDoNothing_whenHomeIsSetUp() {

        when(homeRepository.getHome(any(), anyInt())).thenReturn(Optional.empty());

        String result = homeManage.teleportToHome(UUID.randomUUID());

        verifyNoInteractions(playerTeleporter);
        assertThat(result).isEqualTo("<error>Votre home n°1 n'est pas défini.");
    }

}
