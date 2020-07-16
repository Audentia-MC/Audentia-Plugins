package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.NpcLocation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static fr.audentia.core.domain.model.npc.NpcLocationBuilder.aNpcLocation;
import static org.mockito.Mockito.*;

public class NpcManagerTest {

    private NpcSpawner npcSpawner;
    private NpcRepository npcRepository;
    private NpcManager npcManager;

    @Before
    public void setUp() {
        this.npcSpawner = Mockito.mock(NpcSpawner.class);
        this.npcRepository = Mockito.mock(NpcRepository.class);
        this.npcManager = new NpcManager(npcSpawner, npcRepository);
    }

    @Test
    public void spawnNpc_shouldSpawnNpc_whenNpcExist() {

        NpcLocation npcLocation = aNpcLocation()
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0f)
                .withPitch(0f)
                .build();

        when(npcRepository.getPnjLocation("Tony")).thenReturn(Optional.of(npcLocation));

        npcManager.spawnPnj("Tony");

        verify(npcSpawner, times(1)).spawnNpc(npcLocation);
    }

    @Test
    public void spawnNpc_shouldNotSpawnNpc_whenNpcDoesNotExist() {

        when(npcRepository.getPnjLocation("Tony")).thenReturn(Optional.empty());

        npcManager.spawnPnj("Tony");

        verify(npcSpawner, times(0)).spawnNpc(null);
    }

}
