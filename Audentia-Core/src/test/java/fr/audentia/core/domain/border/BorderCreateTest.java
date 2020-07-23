package fr.audentia.core.domain.border;

import fr.audentia.core.domain.model.border.BorderLocation;
import fr.audentia.core.domain.model.border.BorderSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorderCreateTest {

    @Mock
    private BorderInfosRepository borderInfosRepository;

    @Mock
    private BorderSpawner borderSpawner;

    private BorderCreate borderCreate;

    @BeforeEach
    void setUp() {
        borderCreate = new BorderCreate(borderInfosRepository, borderSpawner);
    }

    @Test
    @DisplayName("createWorldBorder should call borderSpawner when location of the center of the map and the radius of the border are stored")
    void createWorldBorder_shouldCreateWorldBorder_whenInfosAreStored() {

        BorderSize size = new BorderSize(100);
        BorderLocation location = new BorderLocation(0, 0);
        when(borderInfosRepository.getBorderSize()).thenReturn(size);
        when(borderInfosRepository.getBorderLocation()).thenReturn(location);

        borderCreate.createWorldBorder();

        verify(borderSpawner, times(1)).spawnBorder(location, size);
    }

    @Test
    @DisplayName("createWorldBorder shouldn't call borderSpawner when location of the center of the map and the radius of the border aren't stored")
    void createWorldBorder_shouldDoNothing_whenInfosAreNotStored() {

        BorderSize size = new BorderSize(0);
        when(borderInfosRepository.getBorderSize()).thenReturn(size);

        borderCreate.createWorldBorder();

        verifyNoInteractions(borderSpawner);
    }

}
