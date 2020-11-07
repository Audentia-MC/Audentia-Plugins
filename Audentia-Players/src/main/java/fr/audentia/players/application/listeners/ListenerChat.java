package fr.audentia.players.application.listeners;

import fr.audentia.players.domain.teams.MessageFormat;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerChat implements Listener {

    private final MessageFormat messageFormat;

    public ListenerChat(MessageFormat messageFormat) {
        this.messageFormat = messageFormat;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        String message = messageFormat.formatMessage(player.getUniqueId());
        event.setFormat(ChatUtils.formatWithPrefix(message));
    }

}
