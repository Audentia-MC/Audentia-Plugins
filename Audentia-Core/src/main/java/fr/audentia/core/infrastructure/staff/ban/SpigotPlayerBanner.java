package fr.audentia.core.infrastructure.staff.ban;

import fr.audentia.core.domain.staff.ban.PlayerBanner;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotPlayerBanner implements PlayerBanner {

    @Override
    public void ban(UUID bannedUUID) {

        Player player = Bukkit.getPlayer(bannedUUID);
        if (player != null) {
            player.kickPlayer("Vous avez été banni.");
        }

        String name = Bukkit.getOfflinePlayer(bannedUUID).getName();

        if (name == null) {
            return;
        }

        Bukkit.getBanList(BanList.Type.NAME).addBan(name, "Vous avez été banni.", null, "Audentia");

    }

}
