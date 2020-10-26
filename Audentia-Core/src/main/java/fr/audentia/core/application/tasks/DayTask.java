package fr.audentia.core.application.tasks;

import fr.audentia.core.domain.game.GameDayModifier;
import org.bukkit.scheduler.BukkitRunnable;

public class DayTask extends BukkitRunnable {

    private final GameDayModifier gameDayModifier;

    public DayTask(GameDayModifier gameDayModifier) {
        this.gameDayModifier = gameDayModifier;
    }

    @Override
    public void run() {

        gameDayModifier.addDay();
    }

}
