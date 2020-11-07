package fr.audentia.core.infrastructure.game;

import fr.audentia.core.domain.game.PlayerMessageSender;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SpigotPlayerMessageSender implements PlayerMessageSender {

    @Override
    public void sendMessage(UUID playerUUID, String message) {

        Player player = Bukkit.getPlayer(playerUUID);

        if (player == null) {
            return;
        }

        player.sendMessage(ChatUtils.formatWithPrefix(message));
    }

}
