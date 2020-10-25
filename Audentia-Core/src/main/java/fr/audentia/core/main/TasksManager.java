package fr.audentia.core.main;

import fr.audentia.core.application.tasks.*;
import org.bukkit.plugin.Plugin;

public class TasksManager {

    public static final int SCOREBOARD_PERIOD = 10;

    private final Plugin plugin;
    private final AudentiaCoreManagersProvider provider;

    public TasksManager(Plugin plugin, AudentiaCoreManagersProvider provider) {
        this.plugin = plugin;
        this.provider = provider;
    }

    public void startTasks() {

        new ScoreboardTask(provider.scoreboardManage).runTaskTimerAsynchronously(this.plugin, 0, SCOREBOARD_PERIOD);
        new NetherNpcSpawnTask(provider.netherNpcSpawn).runTaskTimerAsynchronously(this.plugin, 20 * 30, SCOREBOARD_PERIOD);
        new NpcSpawnerTask(provider.npcSpawn).runTaskAsynchronously(this.plugin);
        new WorldBorderTask(provider.borderCreate).runTaskAsynchronously(this.plugin);

    }

    public void stopTasks() {

        new NpcRemoverTask(provider.npcSpawn).runTaskAsynchronously(this.plugin);

    }

}
