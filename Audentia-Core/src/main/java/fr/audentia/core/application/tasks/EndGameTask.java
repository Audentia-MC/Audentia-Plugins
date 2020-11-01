package fr.audentia.core.application.tasks;

import fr.audentia.core.domain.game.GameManage;
import org.bukkit.scheduler.BukkitRunnable;

public class EndGameTask extends BukkitRunnable {

    private final GameManage gameManage;

    public EndGameTask(GameManage gameManage) {
        this.gameManage = gameManage;
    }

    @Override
    public void run() {

        gameManage.checkEndGame();
    }

}
