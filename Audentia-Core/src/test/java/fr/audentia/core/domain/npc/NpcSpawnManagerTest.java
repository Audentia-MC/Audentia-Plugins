package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;

import static fr.audentia.core.domain.model.npc.NpcBuilder.aNpcLocation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class NpcSpawnManagerTest {

    private NpcSpawner npcSpawner;
    private NpcRepository npcRepository;
    private WorldNpcFinder worldNpcFinder;
    private NpcSpawnManager npcSpawnManager;

    @Before
    public void setUp() {
        this.npcSpawner = Mockito.mock(NpcSpawner.class);
        this.npcRepository = Mockito.mock(NpcRepository.class);
        this.worldNpcFinder = Mockito.mock(WorldNpcFinder.class);
        this.npcSpawnManager = new NpcSpawnManager(npcSpawner, npcRepository, worldNpcFinder);
    }

    @Test
    public void spawnNpc_shouldSpawnNpc_whenNpcExists() {

        Npc npc = buildNpc("Tony");
        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc));

        npcSpawnManager.spawnNpc("Tony");

        verify(npcSpawner, times(1)).spawnNpc(npc);
    }

    @Test
    public void spawnNpc_shouldNotSpawnNpc_whenNpcDoesNotExists() {

        when(npcRepository.getNpc(anyString())).thenReturn(Optional.empty());

        npcSpawnManager.spawnNpc("Tony");

        verifyNoInteractions(npcSpawner);
    }

    @Test
    public void spawnNpcWithResult_shouldReturnSuccess_whenNpcExists() {

        Npc npc = buildNpc("Tony");
        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc));

        String result = npcSpawnManager.spawnNpcWithResult("Tony");

        verify(npcSpawner, times(1)).spawnNpc(npc);
        assertThat(result).isEqualTo("<success>Le PNJ Tony a bien spawn.");
    }

    @Test
    public void spawnNpcWithResult_shouldReturnError_whenNpcDoesNotExists() {

        when(npcRepository.getNpc(anyString())).thenReturn(Optional.empty());

        String result = npcSpawnManager.spawnNpcWithResult("Tony");

        assertThat(result).isEqualTo("<error>Le PNJ Tony n'a pas été trouvé.");
    }

    @Test
    public void deleteNpc_shouldDeleteNpc_whenNpcLivesInWorld() {

        Npc npc = buildNpc("Tony");
        when(worldNpcFinder.findNpc("Tony")).thenReturn(Optional.of(npc));

        npcSpawnManager.deleteNpc("Tony");

        verify(npcSpawner, times(1)).deleteNpc(npc);
    }

    @Test
    public void deleteNpc_shouldNotDeleteNpc_whenNpcDoesNotLivesInWorld() {

        when(worldNpcFinder.findNpc(anyString())).thenReturn(Optional.empty());

        npcSpawnManager.deleteNpc("Tony");

        verifyNoInteractions(npcSpawner);
    }

    @Test
    public void deleteNpcWithResult_shouldReturnSuccess_whenNpLivesInWorld() {

        Npc npc = buildNpc("Tony");
        when(worldNpcFinder.findNpc("Tony")).thenReturn(Optional.of(npc));

        String result = npcSpawnManager.deleteNpcWithResult("Tony");

        verify(npcSpawner, times(1)).deleteNpc(npc);
        assertThat(result).isEqualTo("<success>Le PNJ Tony a bien été supprimé.");
    }

    @Test
    public void deleteNpcWithResult_shouldReturnError_whenNpcDoesNotLivesInWorld() {

        when(worldNpcFinder.findNpc(anyString())).thenReturn(Optional.empty());

        String result = npcSpawnManager.deleteNpcWithResult("Tony");

        assertThat(result).isEqualTo("<error>Le PNJ Tony n'a pas été trouvé.");
    }

    @Test
    public void spawnAllNpcs_shouldSpawn3Npcs_whenThereAre3NpcsStored() {

        Npc npc1 = buildNpc("Tony");
        Npc npc2 = buildNpc("Manu");
        Npc npc3 = buildNpc("Kevin");
        when(npcRepository.getAllNpc()).thenReturn(Arrays.asList(npc1, npc2, npc3));
        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc1));
        when(npcRepository.getNpc("Manu")).thenReturn(Optional.of(npc2));
        when(npcRepository.getNpc("Kevin")).thenReturn(Optional.of(npc3));

        npcSpawnManager.spawnAllNpcs();

        verify(npcSpawner, times(1)).spawnNpc(npc1);
        verify(npcSpawner, times(1)).spawnNpc(npc2);
        verify(npcSpawner, times(1)).spawnNpc(npc3);
        verifyNoMoreInteractions(npcSpawner);
    }

    @Test
    public void deleteAllNpcs_shouldDelete2Npcs_whenThereAre2NpcsLivesInWorld() {

        Npc npc1 = buildNpc("Tony");
        Npc npc2 = buildNpc("Manu");
        when(worldNpcFinder.findAllNpc()).thenReturn(Arrays.asList(npc1, npc2));
        when(worldNpcFinder.findNpc("Tony")).thenReturn(Optional.of(npc1));
        when(worldNpcFinder.findNpc("Manu")).thenReturn(Optional.of(npc2));

        npcSpawnManager.deleteAllNpcs();

        verify(npcSpawner, times(1)).deleteNpc(npc1);
        verify(npcSpawner, times(1)).deleteNpc(npc2);
    }

    @Test
    public void spawnAllNpcsWithResult_shouldReturn3Success_whenThereAre3NpcsStored() {

        Npc npc1 = buildNpc("Tony");
        Npc npc2 = buildNpc("Manu");
        Npc npc3 = buildNpc("Kevin");
        when(npcRepository.getAllNpc()).thenReturn(Arrays.asList(npc1, npc2, npc3));
        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc1));
        when(npcRepository.getNpc("Manu")).thenReturn(Optional.of(npc2));
        when(npcRepository.getNpc("Kevin")).thenReturn(Optional.of(npc3));

        String result = npcSpawnManager.spawnAllNpcsWithResult();

        assertThat(result).isEqualTo("<success>Le PNJ Tony a bien spawn." +
                "\n<success>Le PNJ Manu a bien spawn.\n" +
                "<success>Le PNJ Kevin a bien spawn.");
    }

    @Test
    public void deleteAllNpcsWithResult_shouldReturn2Success_whenThereAre2NpcsLivesInWorld() {

        Npc npc1 = buildNpc("Tony");
        Npc npc2 = buildNpc("Manu");
        when(worldNpcFinder.findAllNpc()).thenReturn(Arrays.asList(npc1, npc2));
        when(worldNpcFinder.findNpc("Tony")).thenReturn(Optional.of(npc1));
        when(worldNpcFinder.findNpc("Manu")).thenReturn(Optional.of(npc2));

        String result = npcSpawnManager.deleteAllNpcsWithResult();

        assertThat(result).isEqualTo("<success>Le PNJ Tony a bien été supprimé." +
                "\n<success>Le PNJ Manu a bien été supprimé.");
    }

    @Test
    public void reloadNpc_shouldDeleteThenSpawnNpc_whenNpcExists() {

        Npc npc = buildNpc("Tony");
        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc));
        when(worldNpcFinder.findNpc("Tony")).thenReturn(Optional.of(npc));

        npcSpawnManager.reloadNpc("Tony");

        InOrder order = inOrder(npcSpawner, npcSpawner);
        order.verify(npcSpawner).deleteNpc(npc);
        order.verify(npcSpawner).spawnNpc(npc);
        verify(npcSpawner, times(1)).deleteNpc(npc);
        verify(npcSpawner, times(1)).spawnNpc(npc);
    }

    @Test
    public void reloadNpc_shouldDoNothing_whenNpcDoesNotExists() {

        when(npcRepository.getNpc(anyString())).thenReturn(Optional.empty());
        when(worldNpcFinder.findNpc(anyString())).thenReturn(Optional.empty());

        npcSpawnManager.reloadNpc("Tony");

        verifyNoInteractions(npcSpawner);
    }

    @Test
    public void reloadNpcWithResult_shouldReturnSuccess_whenNpcExists() {

        Npc npc = buildNpc("Tony");
        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc));
        when(worldNpcFinder.findNpc("Tony")).thenReturn(Optional.of(npc));

        String result = npcSpawnManager.reloadNpcWithResult("Tony");

        assertThat(result).isEqualTo("<success>Le PNJ Tony a bien été rechargé.");
    }

    @Test
    public void reloadNpcWithResult_shouldReturnError_whenNpcDoesNotExists() {

        when(npcRepository.getNpc(anyString())).thenReturn(Optional.empty());
        when(worldNpcFinder.findNpc(anyString())).thenReturn(Optional.empty());

        String result = npcSpawnManager.reloadNpcWithResult("Tony");

        assertThat(result).isEqualTo("<error>Une erreur s'est produite.");
    }

    @Test
    public void reloadAllNpcs_shouldDeleteThenSpawn3Npcs_when3NpcsExists() {

        Npc npc1 = buildNpc("Tony");
        Npc npc2 = buildNpc("Manu");
        Npc npc3 = buildNpc("Kevin");
        when(npcRepository.getAllNpc()).thenReturn(Arrays.asList(npc1, npc2, npc3));
        when(worldNpcFinder.findAllNpc()).thenReturn(Arrays.asList(npc1, npc2, npc3));
        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc1));
        when(npcRepository.getNpc("Manu")).thenReturn(Optional.of(npc2));
        when(npcRepository.getNpc("Kevin")).thenReturn(Optional.of(npc3));
        when(worldNpcFinder.findNpc("Tony")).thenReturn(Optional.of(npc1));
        when(worldNpcFinder.findNpc("Manu")).thenReturn(Optional.of(npc2));
        when(worldNpcFinder.findNpc("Kevin")).thenReturn(Optional.of(npc3));

        npcSpawnManager.reloadAllNpcs();

        InOrder order = inOrder(npcSpawner, npcSpawner, npcSpawner, npcSpawner, npcSpawner, npcSpawner);
        order.verify(npcSpawner).deleteNpc(npc1);
        order.verify(npcSpawner).deleteNpc(npc2);
        order.verify(npcSpawner).deleteNpc(npc3);
        order.verify(npcSpawner).spawnNpc(npc1);
        order.verify(npcSpawner).spawnNpc(npc2);
        order.verify(npcSpawner).spawnNpc(npc3);
        verifyNoMoreInteractions(npcSpawner);
    }

    @Test
    public void reloadAllNpcs_shouldDelete2NpcsThenSpawn3Npcs_when2NpcsLiveInWorldAnd3AreStored() {

        Npc npc1 = buildNpc("Tony");
        Npc npc2 = buildNpc("Manu");
        Npc npc3 = buildNpc("Kevin");
        when(npcRepository.getAllNpc()).thenReturn(Arrays.asList(npc1, npc2, npc3));
        when(worldNpcFinder.findAllNpc()).thenReturn(Arrays.asList(npc1, npc2));
        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc1));
        when(npcRepository.getNpc("Manu")).thenReturn(Optional.of(npc2));
        when(npcRepository.getNpc("Kevin")).thenReturn(Optional.of(npc3));
        when(worldNpcFinder.findNpc("Tony")).thenReturn(Optional.of(npc1));
        when(worldNpcFinder.findNpc("Manu")).thenReturn(Optional.of(npc2));

        npcSpawnManager.reloadAllNpcs();

        InOrder order = inOrder(npcSpawner, npcSpawner, npcSpawner, npcSpawner, npcSpawner);
        order.verify(npcSpawner).deleteNpc(npc1);
        order.verify(npcSpawner).deleteNpc(npc2);
        order.verify(npcSpawner).spawnNpc(npc1);
        order.verify(npcSpawner).spawnNpc(npc2);
        order.verify(npcSpawner).spawnNpc(npc3);
        verifyNoMoreInteractions(npcSpawner);
    }

    @Test
    public void reloadAllNpcs_shouldReturnMessage() {

        String result = npcSpawnManager.reloadAllNpcsWithResult();

        assertThat(result).isEqualTo("<success>Les PNJ ont bien été rechargés.");
    }

    private Npc buildNpc(String name) {
        return aNpcLocation()
                .withName(name)
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0f)
                .withPitch(0f)
                .build();
    }

}
