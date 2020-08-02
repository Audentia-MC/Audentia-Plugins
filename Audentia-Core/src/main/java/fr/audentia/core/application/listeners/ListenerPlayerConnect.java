package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.scoreboard.ScoreboardManage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPlayerConnect implements Listener { // TODO : disconnect non player ?

    private final ScoreboardManage scoreboardManage;

    public ListenerPlayerConnect(ScoreboardManage scoreboardManage) {
        this.scoreboardManage = scoreboardManage;
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        scoreboardManage.removeScoreBoard(event.getPlayer().getUniqueId());
    }

}
