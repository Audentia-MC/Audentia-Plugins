package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;

import static fr.audentia.core.domain.model.npc.NpcBuilder.aNpcLocation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class NpcSpawnManagerTest {

    private NpcSpawner npcSpawner;
    private NpcRepository npcRepository;
    private NpcSpawnManager npcSpawnManager;

    @Before
    public void setUp() {
        this.npcSpawner = Mockito.mock(NpcSpawner.class);
        this.npcRepository = Mockito.mock(NpcRepository.class);
        this.npcSpawnManager = new NpcSpawnManager(npcSpawner, npcRepository);
    }

    @Test
    public void spawnNpc_shouldSpawnNpc_whenNpcExist() {

        Npc npc = aNpcLocation()
                .withName("Tony")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0f)
                .withPitch(0f)
                .build();

        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc));

        npcSpawnManager.spawnNpc("Tony");

        verify(npcSpawner, times(1)).spawnNpc(npc);
    }

    @Test
    public void spawnNpcWithResult_shouldReturnSuccess_whenNpcExist() {

        Npc npc = aNpcLocation()
                .withName("Tony")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0f)
                .withPitch(0f)
                .build();

        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc));

        String result = npcSpawnManager.spawnNpcWithResult("Tony");

        assertThat(result).isEqualTo("<success>Le PNJ Tony a bien spawn.");
    }

    @Test
    public void spawnNpcWithResult_shouldReturnError_whenNpcDoesNotExist() {

        when(npcRepository.getNpc("Tony")).thenReturn(Optional.empty());

        String result = npcSpawnManager.spawnNpcWithResult("Tony");

        assertThat(result).isEqualTo("<error>Le PNJ Tony n'a pas été trouvé.");
    }

    @Test
    public void deleteNpc_shouldDeleteNpc_whenNpcExist() {

        Npc npc = aNpcLocation()
                .withName("Tony")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0f)
                .withPitch(0f)
                .build();

        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc));

        npcSpawnManager.deleteNpc("Tony");

        verify(npcSpawner, times(1)).deleteNpcNpc(npc);
    }

    @Test
    public void deleteNpcWithResult_shouldReturnSuccess_whenNpcExist() {

        Npc npc = aNpcLocation()
                .withName("Tony")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0f)
                .withPitch(0f)
                .build();

        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc));

        String result = npcSpawnManager.deleteNpcWithResult("Tony");

        assertThat(result).isEqualTo("<success>Le PNJ Tony a bien été supprimé.");
    }

    @Test
    public void deleteNpcWithResult_shouldReturnError_whenNpcDoesNotExist() {

        when(npcRepository.getNpc("Tony")).thenReturn(Optional.empty());

        String result = npcSpawnManager.deleteNpcWithResult("Tony");

        assertThat(result).isEqualTo("<error>Le PNJ Tony n'a pas été trouvé.");
    }

    @Test
    public void spawnAllNpc_shouldSpawn3Npc_whenThereAre3NpcStored() {

        Npc npc1 = aNpcLocation()
                .withName("Tony")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0f)
                .withPitch(0f)
                .build();

        Npc npc2 = aNpcLocation()
                .withName("Manu")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0f)
                .withPitch(0f)
                .build();

        Npc npc3 = aNpcLocation()
                .withName("Kevin")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0f)
                .withPitch(0f)
                .build();

        when(npcRepository.getAllNpc()).thenReturn(Arrays.asList(npc1, npc2, npc3));
        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc1));
        when(npcRepository.getNpc("Manu")).thenReturn(Optional.of(npc2));
        when(npcRepository.getNpc("Kevin")).thenReturn(Optional.of(npc3));

        npcSpawnManager.spawnAllNpc();

        verify(npcSpawner, times(1)).spawnNpc(npc1);
        verify(npcSpawner, times(1)).spawnNpc(npc2);
        verify(npcSpawner, times(1)).spawnNpc(npc3);
    }

}
