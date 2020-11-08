package fr.audentia.core.application.listeners;

import fr.audentia.core.domain.players.JoinActions;
import fr.audentia.core.domain.scoreboard.ScoreboardManage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPlayerConnect implements Listener {

    private final ScoreboardManage scoreboardManage;
    private final JoinActions joinActions;

    public ListenerPlayerConnect(ScoreboardManage scoreboardManage, JoinActions joinActions) {
        this.scoreboardManage = scoreboardManage;
        this.joinActions = joinActions;
    }

    @EventHandler
    public void onConnect(PlayerJoinEvent event) {
        boolean kick = joinActions.onJoin(event.getPlayer().getUniqueId());

        if (kick) {
            event.getPlayer().kickPlayer("Vous n'êtes pas autorisé à vous connecter");
        }

    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        scoreboardManage.removeScoreBoard(event.getPlayer().getUniqueId());
    }

}
