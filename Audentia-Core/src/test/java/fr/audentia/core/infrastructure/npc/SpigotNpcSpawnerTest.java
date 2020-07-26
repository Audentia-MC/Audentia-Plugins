package fr.audentia.core.infrastructure.npc;

import edu.emory.mathcs.backport.java.util.Arrays;
import fr.audentia.core.domain.model.npc.Npc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static fr.audentia.core.domain.model.npc.NpcBuilder.aNpc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpigotNpcSpawnerTest {

    @Mock
    private World world;

    @Mock
    private Entity entity;

    private MockedStatic<Bukkit> bukkitMockedStatic;

    private SpigotNpcSpawner spigotNpcSpawner;

    @BeforeEach
    void setUp() {
        bukkitMockedStatic = mockStatic(Bukkit.class);
        spigotNpcSpawner = new SpigotNpcSpawner();
    }

    @AfterEach
    void tieDown() {
        bukkitMockedStatic.close();
    }

    @Test
    @DisplayName("spawnNpc should spawn the npc in the default world when this world exists")
    void spawnNpc_shouldSpawnAnNpc_whenWorldDefaultExists() {

        Npc npc = aNpc()
                .withName("Tony")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0)
                .withPitch(0)
                .build();
        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(world);
        when(world.spawnEntity(any(), eq(EntityType.VILLAGER))).thenReturn(entity);

        spigotNpcSpawner.spawnNpc(npc);

        verify(world, times(1)).spawnEntity(any(), eq(EntityType.VILLAGER));
        verify(entity, times(1)).setCustomNameVisible(true);
        verify(entity, times(1)).setCustomName("Tony");
        verify(entity, times(1)).setInvulnerable(true);
        verify(entity, times(1)).setVelocity(new Vector(0, 0, 0));
    }



    @Test
    @DisplayName("spawnNpc should do nothing when the default world does not exist")
    void spawnNpc_shouldDoNothing_whenWorldDefaultDoesNotExist() {

        Npc npc = aNpc()
                .withName("Tony")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0)
                .withPitch(0)
                .build();
        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(null);

        spigotNpcSpawner.spawnNpc(npc);

        verifyNoInteractions(world);
        verifyNoInteractions(entity);
    }

    @Test
    @DisplayName("deleteNpc should delete the npc from the default world when this world exists and npc exists")
    void deleteNpc_shouldDeleteNpc_whenWorldDefaultExistsAndNpcExists() {

        Npc npc = aNpc()
                .withName("Tony")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0)
                .withPitch(0)
                .build();
        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(world);
        when(world.getNearbyEntities(new Location(world, 0, 0, 0), 1, 1, 1)).thenReturn(Collections.singletonList(entity));

        spigotNpcSpawner.deleteNpc(npc);

        verify(world, times(1)).getNearbyEntities(new Location(world, 0, 0, 0), 1, 1, 1);
        verify(entity, times(1)).remove();
    }

    @Test
    @DisplayName("deleteNpc should do nothing when the default world does not exist")
    void deleteNpc_shouldDoNothing_whenWorldDefaultDoesNotExists() {

        Npc npc = aNpc()
                .withName("Tony")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0)
                .withPitch(0)
                .build();
        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(null);

        spigotNpcSpawner.deleteNpc(npc);

        verifyNoInteractions(world);
        verifyNoInteractions(entity);
    }

    @Test
    @DisplayName("deleteNpc should do nothing when the npc does not exist")
    void deleteNpc_shouldDoNothing_whenNpcDoesNotExists() {

        Npc npc = aNpc()
                .withName("Tony")
                .withX(0)
                .withY(0)
                .withZ(0)
                .withYaw(0)
                .withPitch(0)
                .build();
        bukkitMockedStatic.when(() -> Bukkit.getWorld("world")).thenReturn(world);
        when(world.getNearbyEntities(new Location(world, 0, 0, 0), 1, 1, 1)).thenReturn(Collections.emptyList());

        spigotNpcSpawner.deleteNpc(npc);

        verify(world, times(1)).getNearbyEntities(new Location(world, 0, 0, 0), 1, 1, 1);
        verifyNoInteractions(entity);
    }

}
