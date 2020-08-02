package fr.audentia.core.infrastructure.staff.teleport;

import fr.audentia.core.domain.staff.teleport.PlayerTeleporter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotPlayerTeleporter implements PlayerTeleporter {

    @Override
    public void teleport(UUID playerUUID, UUID targetUUID) {

        Player player = Bukkit.getPlayer(playerUUID);
        Player target = Bukkit.getPlayer(targetUUID);

        if (player == null || target == null) {
            return;
        }

        player.teleport(target);

    }

}
