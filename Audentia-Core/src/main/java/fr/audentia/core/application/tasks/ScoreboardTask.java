package fr.audentia.core.application.tasks;

import fr.audentia.core.domain.scoreboard.ScoreboardManage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardTask extends BukkitRunnable {

    private final ScoreboardManage scoreboardManage;

    public ScoreboardTask(ScoreboardManage scoreboardManage) {
        this.scoreboardManage = scoreboardManage;
    }

    @Override
    public void run() {

        Bukkit.getOnlinePlayers().stream()
                .map(Player::getUniqueId)
                .forEach(scoreboardManage::updateScoreboard);
    }

}
