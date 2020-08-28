package fr.audentia.players.main;

import fr.audentia.players.application.tasks.TabListTask;
import org.bukkit.plugin.Plugin;

public class TasksManager {

    private final Plugin plugin;
    private final AudentiaPlayersManagersProvider provider;

    public TasksManager(Plugin plugin, AudentiaPlayersManagersProvider provider) {
        this.plugin = plugin;
        this.provider = provider;
    }

    public void startTasks() {

        new TabListTask(provider.tabListProvider).runTaskTimerAsynchronously(this.plugin, 0, 20);

    }

}
