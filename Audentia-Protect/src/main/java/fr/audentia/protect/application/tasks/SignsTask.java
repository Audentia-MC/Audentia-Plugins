package fr.audentia.protect.application.tasks;

import fr.audentia.protect.domain.house.SignsManage;
import org.bukkit.scheduler.BukkitRunnable;

public class SignsTask extends BukkitRunnable {

    private final SignsManage signsManage;

    public SignsTask(SignsManage signsManage) {
        this.signsManage = signsManage;
    }

    @Override
    public void run() {

        signsManage.forceReloadAllSigns();
    }

}
