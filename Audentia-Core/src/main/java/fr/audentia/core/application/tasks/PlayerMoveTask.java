package fr.audentia.core.application.tasks;

import fr.audentia.core.domain.home.TeleportationsManage;
import fr.audentia.core.domain.players.MoveManage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerMoveTask extends BukkitRunnable {

    private final Map<UUID, Location> locations = new HashMap<>();
    private final MoveManage moveManage;
    private final TeleportationsManage teleportationsManage;

    public PlayerMoveTask(MoveManage moveManage, TeleportationsManage teleportationsManage) {
        this.moveManage = moveManage;
        this.teleportationsManage = teleportationsManage;
    }

    @Override
    public void run() {

        Bukkit.getOnlinePlayers().forEach(this::computePlayer);
    }

    private void computePlayer(Player player) {

        UUID uuid = player.getUniqueId();
        Location location = player.getLocation();

        if (!locations.containsKey(uuid)) {
            locations.put(uuid, location);
        }

        Location oldLocation = locations.get(uuid);

        if (location.getWorld() != oldLocation.getWorld() || location.distanceSquared(oldLocation) != 0) {

            teleportationsManage.cancelIfRegistered(player.getUniqueId());

            if (!moveManage.canMove(uuid)) {
                player.teleport(oldLocation);
                return;
            }

        }

        locations.put(uuid, location);
    }

}
