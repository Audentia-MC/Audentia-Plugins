package fr.audentia.core.infrastructure.npc;

import fr.audentia.core.domain.model.npc.Npc;
import fr.audentia.core.domain.model.npc.NpcBuilder;
import fr.audentia.core.domain.npc.WorldNpcFinder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Villager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpigotWorldNpcFinder implements WorldNpcFinder {

    @Override
    public Optional<Npc> findNpc(String npcName) {
        return findNpc(npcName, "world");
    }

    @Override
    public Optional<Npc> findNpc(String npcName, String worldName) {

        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            return Optional.empty();
        }

        Npc npc = world.getEntitiesByClass(Villager.class)
                .stream()
                .filter(villager -> villager.getCustomName() != null)
                .filter(villager -> villager.getCustomName().equalsIgnoreCase(npcName))
                .map(Villager::getLocation)
                .map(location -> NpcBuilder.aNpc()
                        .withName(npcName)
                        .withX(location.getBlockX())
                        .withX(location.getBlockY())
                        .withX(location.getBlockZ())
                        .withYaw(location.getYaw())
                        .withPitch(location.getPitch())
                        .build())
                .findAny()
                .orElse(null);

        return Optional.ofNullable(npc);
    }

    @Override
    public List<Npc> findAllNpc() {
        World world = Bukkit.getWorld("world");

        if (world == null) {
            return Collections.emptyList();
        }

        return world.getEntitiesByClass(Villager.class)
                .stream()
                .filter(villager -> villager.getCustomName() != null)
                .map(villager -> {
                    Location location = villager.getLocation();
                    return NpcBuilder.aNpc()
                            .withName(villager.getCustomName())
                            .withX(location.getBlockX())
                            .withX(location.getBlockY())
                            .withX(location.getBlockZ())
                            .withYaw(location.getYaw())
                            .withPitch(location.getPitch())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
