package fr.audentia.protect.application.listeners;

import fr.audentia.players.utils.ChatUtils;
import fr.audentia.protect.domain.house.HouseCreation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ListenerPlayerChatHouseCreation implements Listener {

    private final HouseCreation houseCreation;

    public ListenerPlayerChatHouseCreation(HouseCreation houseCreation) {
        this.houseCreation = houseCreation;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        if (event.getMessage().isEmpty()) {
            return;
        }

        String entry = event.getMessage().split(" ")[0];

        Player player = event.getPlayer();
        String result = houseCreation.onChat(player.getUniqueId(), entry);

        if (!result.isEmpty()) {
            player.sendMessage(ChatUtils.formatWithPrefix(result));
            event.setCancelled(true);
        }

    }

}
