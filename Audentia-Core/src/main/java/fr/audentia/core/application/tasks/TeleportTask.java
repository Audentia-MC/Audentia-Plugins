package fr.audentia.core.application.tasks;

import fr.audentia.core.domain.home.TeleportationsManage;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportTask extends BukkitRunnable {

    private final TeleportationsManage teleportationsManage;

    public TeleportTask(TeleportationsManage teleportationsManage) {
        this.teleportationsManage = teleportationsManage;
    }

    @Override
    public void run() {

        teleportationsManage.computeTeleportations();
    }

}
