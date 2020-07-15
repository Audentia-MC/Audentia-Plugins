package fr.audentia.core.main;

import org.bukkit.plugin.Plugin;

public class TasksManager {

    private final Plugin plugin;
    private final AudentiaCoreManagersProvider provider;

    public TasksManager(Plugin plugin, AudentiaCoreManagersProvider provider) {
        this.plugin = plugin;
        this.provider = provider;
    }

    public void startTasks() {

//        Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, , 0, 20);

    }

}
