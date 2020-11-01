package fr.audentia.core.infrastructure.home;

import fr.audentia.core.domain.home.PlayerTeleport;
import fr.audentia.core.domain.model.home.Home;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotPlayerTeleport implements PlayerTeleport {

    @Override
    public void teleport(UUID playerUUID, Home home) {

        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) {
            return;
        }

        World world = Bukkit.getWorld("world");

        if (world == null) {
            return;
        }

        player.teleport(new Location(world, home.x, home.y, home.z));

    }

}
