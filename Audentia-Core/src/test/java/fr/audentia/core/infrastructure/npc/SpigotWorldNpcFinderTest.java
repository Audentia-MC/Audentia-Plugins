package fr.audentia.core.infrastructure.npc;

import fr.audentia.core.domain.model.npc.Npc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Villager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static fr.audentia.core.domain.model.npc.NpcBuilder.aNpc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpigotWorldNpcFinderTest {

    @Mock
    private World world;

    @Mock
    private Villager villager;

    private MockedStatic<Bukkit> bukkitMockedStatic;

    private SpigotWorldNpcFinder spigotWorldNpcFinder;

    @BeforeEach
    void setUp() {
        bukkitMockedStatic = mockStatic(Bukkit.class);
        spigotWorldNpcFinder = new SpigotWorldNpcFinder();
    }

    @AfterEach
    void tieDown() {
        bukkitMockedStatic.close();
    }

    @Test
    @DisplayName("findNpc should return Npc when npc is in world")
    void findNpc_shouldReturnNpc_whenNpcIsInWorld() {

        Npc npc = aNpc()
                .withName("Tony")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0)
                .withPitch(0)
                .build();
        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(world);
        when(world.getEntitiesByClass(Villager.class)).thenReturn(Collections.singletonList(villager));
        when(villager.getCustomName()).thenReturn("Tony");
        when(villager.getLocation()).thenReturn(new Location(world, 0, 0, 0, 0, 0));

        Optional<Npc> result = spigotWorldNpcFinder.findNpc("Tony");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(npc);
    }

    @Test
    @DisplayName("findNpc should return nothing when npc isn't in world")
    void findNpc_shouldReturnNothing_whenNpcIsNotInWorld() {

        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(world);
        when(world.getEntitiesByClass(Villager.class)).thenReturn(Collections.emptyList());

        Optional<Npc> result = spigotWorldNpcFinder.findNpc("Tony");

        assertThat(result).isNotPresent();
    }

    @Test
    @DisplayName("findNpc should return nothing when default world does not exist")
    void findNpc_shouldReturnNothing_whenWorldDoesNotExist() {

        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(null);

        Optional<Npc> result = spigotWorldNpcFinder.findNpc("Tony");

        assertThat(result).isNotPresent();
        verifyNoInteractions(world);
    }

    @Test
    @DisplayName("findNpc should return nothing when npc isn't in world")
    void findNpc_shouldReturnNothing_whenNpcIsNotInWorld2() {

        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(world);
        when(world.getEntitiesByClass(Villager.class)).thenReturn(Collections.singletonList(villager));
        when(villager.getCustomName()).thenReturn("Manu");

        Optional<Npc> result = spigotWorldNpcFinder.findNpc("Tony");

        assertThat(result).isNotPresent();
    }

    @Test
    @DisplayName("findNpc should return nothing when npc isn't in world")
    void findNpc_shouldReturnNothing_whenNpcIsNotInWorld3() {

        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(world);
        when(world.getEntitiesByClass(Villager.class)).thenReturn(Collections.singletonList(villager));
        when(villager.getCustomName()).thenReturn(null);

        Optional<Npc> result = spigotWorldNpcFinder.findNpc("Tony");

        assertThat(result).isNotPresent();
    }

    @Test
    @DisplayName("findNpc should return list with 1 npc when there is 1 npc in world")
    void findAllNpc_shouldReturnListWith1Npc_whenThereIS1NpcInWorld() {

        Npc npc = aNpc()
                .withName("Tony")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0)
                .withPitch(0)
                .build();
        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(world);
        when(world.getEntitiesByClass(Villager.class)).thenReturn(Collections.singletonList(villager));
        when(villager.getCustomName()).thenReturn("Tony");
        when(villager.getLocation()).thenReturn(new Location(world, 0, 0, 0, 0, 0));

        List<Npc> result = spigotWorldNpcFinder.findAllNpc();

        assertThat(result.size()).isOne();
        assertThat(result.get(0)).isEqualTo(npc);
    }

    @Test
    @DisplayName("findAllNpc should return empty list when there isn't any npc in world")
    void findAllNpc_shouldReturnEmptyList_whenThereIsNotAnyNpcInWorld() {

        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(world);
        when(world.getEntitiesByClass(Villager.class)).thenReturn(Collections.emptyList());

        List<Npc> result = spigotWorldNpcFinder.findAllNpc();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findAllNpc should return empty list when there isn't any npc in world")
    void findAllNpc_shouldReturnEmptyList_whenThereIsNotAnyNpcInWorld2() {

        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(world);
        when(world.getEntitiesByClass(Villager.class)).thenReturn(Collections.singletonList(villager));
        when(villager.getCustomName()).thenReturn(null);

        List<Npc> result = spigotWorldNpcFinder.findAllNpc();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findAllNpc should return empty list when world does not exist")
    void findAllNpc_shouldReturnEmptyList_whenWorldDoesNotExist() {

        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(null);

        List<Npc> result = spigotWorldNpcFinder.findAllNpc();

        assertThat(result).isEmpty();
    }

}
