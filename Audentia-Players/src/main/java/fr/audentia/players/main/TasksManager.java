package fr.audentia.players.main;

import org.bukkit.plugin.Plugin;

public class TasksManager {

    private final Plugin plugin;
    private final AudentiaPlayersManagersProvider provider;

    public TasksManager(Plugin plugin, AudentiaPlayersManagersProvider provider) {
        this.plugin = plugin;
        this.provider = provider;
    }

    public void startTasks() {

//        Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, , 0, 20);

    }

}