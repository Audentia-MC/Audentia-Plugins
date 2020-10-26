package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.players.JoinGameModeManage;
import fr.audentia.core.domain.scoreboard.ScoreboardManage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPlayerConnect implements Listener {

    private final ScoreboardManage scoreboardManage;
    private final JoinGameModeManage joinGameModeManage;

    public ListenerPlayerConnect(ScoreboardManage scoreboardManage, JoinGameModeManage joinGameModeManage) {
        this.scoreboardManage = scoreboardManage;
        this.joinGameModeManage = joinGameModeManage;
    }

    @EventHandler
    public void onConnect(PlayerJoinEvent event) {
        joinGameModeManage.onJoin(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        scoreboardManage.removeScoreBoard(event.getPlayer().getUniqueId());
    }

}
