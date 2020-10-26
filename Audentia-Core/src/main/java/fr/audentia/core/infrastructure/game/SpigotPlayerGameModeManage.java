package fr.audentia.core.infrastructure.game;

import fr.audentia.core.domain.game.PlayerGameModeManage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotPlayerGameModeManage implements PlayerGameModeManage {

    @Override
    public void changeGameModeToSurvival(UUID playerUUID) {

        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) {
            return;
        }

        player.setGameMode(GameMode.SURVIVAL);
    }

}
