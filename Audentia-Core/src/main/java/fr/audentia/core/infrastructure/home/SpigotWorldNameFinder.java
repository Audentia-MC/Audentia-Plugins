package fr.audentia.core.infrastructure.home;

import fr.audentia.core.domain.home.WorldNameFinder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotWorldNameFinder implements WorldNameFinder {

    @Override
    public String getWorldName(UUID playerUUID) {

        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) {
            return "";
        }

        return player.getWorld().getName();
    }

}
