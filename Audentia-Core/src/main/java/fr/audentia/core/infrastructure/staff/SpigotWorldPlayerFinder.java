package fr.audentia.core.infrastructure.staff;

import fr.audentia.core.domain.staff.WorldPlayerFinder;
import org.bukkit.Bukkit;

import java.util.UUID;

public class SpigotWorldPlayerFinder implements WorldPlayerFinder {

    @Override
    public boolean isInWorld(String playerName) {
        return Bukkit.getPlayer(playerName) != null;
    }

    @Override
    public boolean isInWorld(UUID playerUUID) {
        return Bukkit.getPlayer(playerUUID) != null;
    }

}
