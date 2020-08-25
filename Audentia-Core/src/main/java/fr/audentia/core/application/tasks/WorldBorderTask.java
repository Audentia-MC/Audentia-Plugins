package fr.audentia.core.application.tasks;

import fr.audentia.core.domain.border.BorderCreate;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldBorderTask extends BukkitRunnable {

    private final BorderCreate borderCreate;

    public WorldBorderTask(BorderCreate borderCreate) {
        this.borderCreate = borderCreate;
    }

    @Override
    public void run() {

        borderCreate.createWorldBorder();

    }

}
