package fr.audentia.core.infrastructure.npc;

import fr.audentia.core.domain.model.npc.Npc;
import fr.audentia.core.domain.npc.NpcSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.util.Vector;

public class SpigotNpcSpawner implements NpcSpawner {

    public static final String DEFAULT_WORLD_NAME = "world";

    @Override
    public void spawnNpc(Npc npc) {
        spawnNpc(npc, DEFAULT_WORLD_NAME);
    }

    @Override
    public void spawnNpc(Npc npc, String worldName) {
        Location location = buildLocation(npc, worldName);

        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            return;
        }

        Villager entity = (Villager) world.spawnEntity(location, EntityType.VILLAGER);
        entity.setCustomNameVisible(true);
        entity.setCustomName(npc.name);
        entity.setInvulnerable(true);
        entity.setVelocity(new Vector(0, 0, 0));
        entity.setAI(false);
        entity.setInvulnerable(true);
    }

    @Override
    public void deleteNpc(Npc npc) {
        deleteNpc(npc, DEFAULT_WORLD_NAME);
    }

    @Override
    public void deleteNpc(Npc npc, String worldName) {

        Location location = buildLocation(npc, worldName);
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            return;
        }

        world.getNearbyEntities(location, 2, 2, 2)
                .stream()
                .filter(entity -> entity instanceof Villager)
                .findAny()
                .ifPresent(Entity::remove);
    }

    private Location buildLocation(Npc npc, String worldName) {

        return new Location(Bukkit.getWorld(worldName),
                npc.x,
                npc.y,
                npc.z,
                npc.yaw,
                npc.pitch);
    }

}
