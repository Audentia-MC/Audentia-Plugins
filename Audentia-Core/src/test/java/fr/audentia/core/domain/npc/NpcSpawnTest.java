package fr.audentia.core.domain.npc;

import fr.audentia.core.domain.model.npc.Npc;
import fr.audentia.players.domain.teams.RolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static fr.audentia.core.domain.model.npc.NpcBuilder.aNpc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NpcSpawnTest {

    @Mock
    private NpcSpawner npcSpawner;

    @Mock
    private NpcRepository npcRepository;

    @Mock
    private WorldNpcFinder worldNpcFinder;

    @Mock
    private RolesRepository rolesRepository;

    private NpcSpawn npcSpawn;

    @BeforeEach
    void setUp() {
        this.npcSpawn = new NpcSpawn(npcSpawner, npcRepository, worldNpcFinder, rolesRepository);
    }

    @Test
    @DisplayName("spawnNpc should call npcSpawner when npc name matches with a npc stored")
    void spawnNpc_shouldSpawnNpc_whenNpcStored() {

        Npc npc = buildNpc("Tony");
        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc));

        String result = npcSpawn.spawnNpc("Tony");

        verify(npcSpawner, times(1)).spawnNpc(npc);
        assertThat(result).isEqualTo("<success>Le PNJ Tony a bien spawn.");
    }

    @Test
    @DisplayName("spawnNpc shouldn't call npcSpawner when npc name doesn't match with any npc stored")
    void spawnNpc_shouldNotSpawnNpc_whenNpcIsNotStored() {

        when(npcRepository.getNpc(anyString())).thenReturn(Optional.empty());

        String result = npcSpawn.spawnNpc("Tony");

        verifyNoInteractions(npcSpawner);
        assertThat(result).isEqualTo("<error>Le PNJ Tony n'a pas été trouvé.");
    }

    @Test
    @DisplayName("deleteNpc should call npcSpawner when npc name matches with a npc living in world")
    void deleteNpc_shouldSpawnNpc_whenNpcLivesInWorld() {

        Npc npc = buildNpc("Tony");
        when(worldNpcFinder.findNpc("Tony")).thenReturn(Optional.of(npc));

        String result = npcSpawn.deleteNpc("Tony");

        verify(npcSpawner, times(1)).deleteNpc(npc);
        assertThat(result).isEqualTo("<success>Le PNJ Tony a bien été supprimé.");
    }

    @Test
    @DisplayName("deleteNpc shouldn't call npcSpawner when npc name doesn't match with any npc living in world")
    void deleteNpc_shouldNotSpawnNpc_whenNpcDoesNotLiveInWorld() {

        when(worldNpcFinder.findNpc(anyString())).thenReturn(Optional.empty());

        String result = npcSpawn.deleteNpc("Tony");

        verifyNoInteractions(npcSpawner);
        assertThat(result).isEqualTo("<error>Le PNJ Tony n'a pas été trouvé.");
    }

    @Test
    @DisplayName("spawnAllNpc should call npcSpawner 3 times when there are 3 npc stored")
    void spawnAllNpcs_shouldSpawn3Npcs_when3NpcsStored() {

        Npc npc1 = buildNpc("Tony");
        Npc npc2 = buildNpc("Manu");
        Npc npc3 = buildNpc("Kevin");
        when(npcRepository.getAllNpc()).thenReturn(Arrays.asList(npc1, npc2, npc3));
        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc1));
        when(npcRepository.getNpc("Manu")).thenReturn(Optional.of(npc2));
        when(npcRepository.getNpc("Kevin")).thenReturn(Optional.of(npc3));

        String result = npcSpawn.spawnAllNpcs();

        verify(npcSpawner, times(1)).spawnNpc(npc1);
        verify(npcSpawner, times(1)).spawnNpc(npc2);
        verify(npcSpawner, times(1)).spawnNpc(npc3);
        assertThat(result).isEqualTo("<success>Le PNJ Tony a bien spawn." +
                "\n<success>Le PNJ Manu a bien spawn.\n" +
                "<success>Le PNJ Kevin a bien spawn.");
    }

    @Test
    @DisplayName("deleteAllNpc should call npcSpawner 2 times when there are 2 npc living in world")
    void deleteAllNpcs_shouldDelete2Npc_when2NpcsLiveInWorld() {

        Npc npc1 = buildNpc("Tony");
        Npc npc2 = buildNpc("Manu");
        when(worldNpcFinder.findAllNpc()).thenReturn(Arrays.asList(npc1, npc2));
        when(worldNpcFinder.findNpc("Tony")).thenReturn(Optional.of(npc1));
        when(worldNpcFinder.findNpc("Manu")).thenReturn(Optional.of(npc2));

        String result = npcSpawn.deleteAllNpcs();

        verify(npcSpawner, times(1)).deleteNpc(npc1);
        verify(npcSpawner, times(1)).deleteNpc(npc2);
        assertThat(result).isEqualTo("<success>Le PNJ Tony a bien été supprimé." +
                "\n<success>Le PNJ Manu a bien été supprimé.");
    }

    @Test
    @DisplayName("reloadNpc should delete then spawn npc when it is living in world and stored")
    void reloadNpc_shouldDeleteThenSpawnNpc_whenNpcLivesInWorldAndStored() {

        Npc npc = buildNpc("Tony");
        when(npcRepository.getNpc("Tony")).thenReturn(Optional.of(npc));
        when(worldNpcFinder.findNpc("Tony")).thenReturn(Optional.of(npc));

        String result = npcSpawn.reloadNpc(Mockito.any(), "Tony");

        InOrder order = inOrder(npcSpawner, npcSpawner);
        order.verify(npcSpawner).deleteNpc(npc);
        order.verify(npcSpawner).spawnNpc(npc);
        verify(npcSpawner, times(1)).deleteNpc(npc);
        verify(npcSpawner, times(1)).spawnNpc(npc);
        assertThat(result).isEqualTo("<success>Le PNJ Tony a bien été rechargé.");
    }

    @Test
    @DisplayName("reloadNpc should do nothing when it isn't living in world and not stored")
    void reloadNpc_shouldDoNothing_whenNpcDoesNotLiveInWorldAndStored() {

        when(worldNpcFinder.findNpc(anyString())).thenReturn(Optional.empty());

        String result = npcSpawn.reloadNpc(Mockito.any(), "Tony");

        verifyNoInteractions(npcSpawner);
        assertThat(result).isEqualTo("<error>Une erreur s'est produite.");
    }

    @Test
    @DisplayName("reloadNpc should delete then spawn 3 npc when there are 3 npc living in world and stored")
    void reloadAllNpcs_shouldDeleteThenSpawn3Npcs_when3NpcsLiveInWorldAndStored() {

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

        String result = npcSpawn.reloadAllNpcs();

        InOrder order = inOrder(npcSpawner, npcSpawner, npcSpawner, npcSpawner, npcSpawner, npcSpawner);
        order.verify(npcSpawner).deleteNpc(npc1);
        order.verify(npcSpawner).deleteNpc(npc2);
        order.verify(npcSpawner).deleteNpc(npc3);
        order.verify(npcSpawner).spawnNpc(npc1);
        order.verify(npcSpawner).spawnNpc(npc2);
        order.verify(npcSpawner).spawnNpc(npc3);
        verifyNoMoreInteractions(npcSpawner);
        assertThat(result).isEqualTo("<success>Les PNJ ont bien été rechargés.");
    }

    @Test
    @DisplayName("reloadNpc should delete 2 npc then spawn 3 npc when there are 2 npc living in world and only 3 stored")
    void reloadAllNpcs_shouldDelete2NpcsThenSpawn3Npcs_when2NpcsLiveInWorldAnd3Stored() {

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

        String result = npcSpawn.reloadAllNpcs();

        InOrder order = inOrder(npcSpawner, npcSpawner, npcSpawner, npcSpawner, npcSpawner);
        order.verify(npcSpawner).deleteNpc(npc1);
        order.verify(npcSpawner).deleteNpc(npc2);
        order.verify(npcSpawner).spawnNpc(npc1);
        order.verify(npcSpawner).spawnNpc(npc2);
        order.verify(npcSpawner).spawnNpc(npc3);
        verifyNoMoreInteractions(npcSpawner);
        assertThat(result).isEqualTo("<success>Les PNJ ont bien été rechargés.");
    }

    private Npc buildNpc(String name) {

        return aNpc()
                .withName(name)
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0f)
                .withPitch(0f)
                .build();
    }

}
