package fr.audentia.protect.main;

import fr.audentia.protect.application.tasks.BuyHouseClicksTasks;
import fr.audentia.protect.application.tasks.SignsTask;
import org.bukkit.plugin.Plugin;

public class TasksManager {

    private final Plugin plugin;
    private final AudentiaProtectManagersProvider provider;

    public TasksManager(Plugin plugin, AudentiaProtectManagersProvider provider) {
        this.plugin = plugin;
        this.provider = provider;
    }

    public void startTasks() {

        new BuyHouseClicksTasks(provider.buyHouseAction).runTaskTimerAsynchronously(plugin, 0, 20);
        new SignsTask(provider.signsManage).runTask(plugin);

    }

}
