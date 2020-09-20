package fr.audentia.protect.main;

import org.bukkit.plugin.Plugin;

public class TasksManager {

    private final Plugin plugin;
    private final AudentiaProtectManagersProvider provider;

    public TasksManager(Plugin plugin, AudentiaProtectManagersProvider provider) {
        this.plugin = plugin;
        this.provider = provider;
    }

    public void startTasks() {

//        Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, , 0, 20);

    }

}
