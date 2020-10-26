package fr.audentia.core.domain.game;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SpigotPlayerFinder implements PlayerFinder {

    @Override
    public List<UUID> getAllPlayers() {

        return Arrays.stream(Bukkit.getOfflinePlayers())
                .map(OfflinePlayer::getUniqueId)
                .collect(Collectors.toList());
    }

}
