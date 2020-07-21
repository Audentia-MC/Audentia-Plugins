package fr.audentia.core.infrastructure.npc;

import fr.audentia.core.domain.model.npc.Npc;
import fr.audentia.core.domain.npc.NpcSpawner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

public class SpigotNpcSpawner implements NpcSpawner {

    public static final String DEFAULT_WORLD_NAME = "world";

    @Override
    public void spawnNpc(Npc npc) {
        Location location = buildLocation(npc);

        World world = Bukkit.getWorld(DEFAULT_WORLD_NAME);

        if (world == null) {
            return;
        }

        Entity entity = world.spawnEntity(location, EntityType.VILLAGER);
        entity.setCustomNameVisible(true);
        entity.setCustomName(npc.name);
        entity.setInvulnerable(true);
        entity.setVelocity(new Vector(0, 0, 0));
    }

    @Override
    public void deleteNpc(Npc npc) {
        Location location = buildLocation(npc);

        World world = Bukkit.getWorld("world");

        if (world == null) {
            return;
        }

        world.getNearbyEntities(location, 1, 1, 1)
                .stream()
                .findAny()
                .ifPresent(Entity::remove);
    }

    private Location buildLocation(Npc npc) {

        return new Location(Bukkit.getWorld("world"),
                npc.x,
                npc.y,
                npc.z,
                npc.yaw,
                npc.pitch);
    }

}
