package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.HomeLocation;
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
class SetHomeManageTest {

    @Mock
    private HomeRepository homeRepository;

    @Mock
    private WorldNameFinder worldNameFinder;

    @Mock
    private RolesRepository rolesRepository;

    private SetHomeManage setHomeManager;

    @BeforeEach
    void setUp() {
        setHomeManager = new SetHomeManage(homeRepository, rolesRepository, worldNameFinder);
    }

    @Test
    @DisplayName("setHome should define new home 1 when player has default player role and is in default world")
    void setHome_shouldStoreHome1_whenPlayerIsDefaultPlayerAndIsInDefaultWorld() {

        HomeLocation homeLocation = new HomeLocation(0, 0, 0);
        when(rolesRepository.getRole(any(UUID.class))).thenReturn(new Role(1, false, false, 1));
        when(worldNameFinder.getWorldName(any(UUID.class))).thenReturn("world");

        String result = setHomeManager.saveHome(UUID.randomUUID(), homeLocation);

        verify(homeRepository, times(1)).saveHome(any(UUID.class), eq(1), eq(homeLocation));
        assertThat(result).isEqualTo("<success>Nouveau home n°1 défini.");
    }

    @Test
    @DisplayName("setHome should do nothing when player role does not allow to have 2 homes")
    void setHome_shouldDoNothing_whenPlayerCantHave2Homes() {

        when(rolesRepository.getRole(any(UUID.class))).thenReturn(new Role(1, false, false, 1));

        String result = setHomeManager.saveHome(UUID.randomUUID(), 2, any(HomeLocation.class));

        verifyNoInteractions(homeRepository);
        assertThat(result).isEqualTo("<error>Votre role ne vous permet pas d'avoir un home n°2.");
    }

    @Test
    @DisplayName("setHome should do nothing when player is not in default world")
    void setHome_shouldDoNothing_whenPlayerIsNotInDefaultWorld() {

        when(rolesRepository.getRole(any(UUID.class))).thenReturn(new Role(1, false, false, 1));
        when(worldNameFinder.getWorldName(any(UUID.class))).thenReturn("nether");

        String result = setHomeManager.saveHome(UUID.randomUUID(), 1, any(HomeLocation.class));

        verifyNoInteractions(homeRepository);
        assertThat(result).isEqualTo("<error>Les homes ne sont disponibles que dans le monde normal.");
    }

}