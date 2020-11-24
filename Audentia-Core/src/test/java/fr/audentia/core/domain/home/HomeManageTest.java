package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.Home;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HomeManageTest {

    @Mock
    private PlayerTeleport playerTeleporter;

    @Mock
    private TeleportRepository teleportRepository;

    @Mock
    private HomeRepository homeRepository;

    private HomeManage homeManage;

    @BeforeEach
    void setUp() {
        homeManage = new HomeManage(homeRepository, teleportRepository, playerTeleporter);
    }

    @Test
    @DisplayName("home 1 should tp player to home n°1 when it is set up")
    void home_shouldTpPlayerToHisHome_whenHomeIsSetUp() {

        Home home = new Home("", 0, 0, 0);
        when(homeRepository.getHome(any(), anyString())).thenReturn(Optional.of(home));

        String result = homeManage.registerTeleport(UUID.randomUUID(), anyString());

        assertThat(result).isEqualTo("<success>Téléportation dans :");
    }

    @Test
    @DisplayName("home 1 should do nothing when home isn't set up")
    void home_shouldDoNothing_whenHomeIsSetUp() {

        when(homeRepository.getHome(any(), anyString())).thenReturn(Optional.empty());

        String result = homeManage.registerTeleport(UUID.randomUUID(), anyString());

        assertThat(result).isEqualTo("<error>Votre home n°1 n'est pas défini.");
    }

}
