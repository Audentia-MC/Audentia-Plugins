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

        new PlayerMoveTask(provider.moveManage, provider.teleportationsManage).runTaskTimer(this.plugin, 0, 15);
        new ScoreboardTask(provider.scoreboardManage).runTaskTimerAsynchronously(this.plugin, 0, SCOREBOARD_PERIOD);
        new NetherNpcSpawnTask(provider.netherNpcSpawn).runTaskTimer(this.plugin, 0, 20 * 30);
        new TeleportTask(provider.teleportationsManage).runTaskTimer(this.plugin, 0, 20);
        new NpcSpawnerTask(provider.npcSpawn, provider.bankNpcSpawn).runTask(this.plugin);
        new WorldBorderTask(provider.borderCreate).runTaskAsynchronously(this.plugin);
        new EndGameTask(provider.gameManage).runTaskTimerAsynchronously(this.plugin, 0, 20 * 30);

    }

    public void stopTasks() {

        new NpcRemoverTask(provider.npcSpawn, provider.netherNpcSpawn, provider.bankNpcSpawn).run();

    }

}
