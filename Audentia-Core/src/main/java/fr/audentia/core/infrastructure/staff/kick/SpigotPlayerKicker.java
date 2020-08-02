package fr.audentia.core.infrastructure.staff.kick;

import fr.audentia.core.domain.staff.kick.PlayerKicker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotPlayerKicker implements PlayerKicker {

    @Override
    public void kick(UUID kickedUUID) {

        Player player = Bukkit.getPlayer(kickedUUID);

        if (player != null) {
            player.kickPlayer("Vous avez été kick.");
        }

    }

}
