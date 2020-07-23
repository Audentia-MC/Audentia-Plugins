package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.HomeLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HomesTest {

    @Mock
    private HomeRepository homeRepository;

    private HomesProvide homesProvide;

    @BeforeEach
    void setUp() {
        this.homesProvide = new HomesProvide(homeRepository);
    }

    @Test
    @DisplayName("homes should return list of homes")
    void homes_shouldReturnListOfHomes_whenPlayerHasHomesStored() {

        List<HomeLocation> homeLocations = Arrays.asList(
                new HomeLocation(0, 0, 0),
                new HomeLocation(1, 2, 3),
                new HomeLocation(4, 1, 2)
        );
        when(homeRepository.getHomes(any())).thenReturn(homeLocations);

        String result = homesProvide.getHomes(UUID.randomUUID());

        assertThat(result).isEqualTo("<success>Liste de vos homes :\n1 - x: 0, y: 0, z: 0\n2 - x: 1, y: 2, z: 3\n3 - x: 4, y: 1, z: 2");
    }

    @Test
    @DisplayName("homes should return simple message when player hasn't any home")
    void homes_shouldReturnSimpleMessage_whenPlayerHasNotAnyHomeStored() {

        when(homeRepository.getHomes(any())).thenReturn(new ArrayList<>());

        String result = homesProvide.getHomes(UUID.randomUUID());

        assertThat(result).isEqualTo("<error>Vous n'avez aucun home défini.");
    }


}
