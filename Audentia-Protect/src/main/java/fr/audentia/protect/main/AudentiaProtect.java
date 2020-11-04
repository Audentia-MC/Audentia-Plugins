package fr.audentia.protect.main;

import fr.audentia.core.main.AudentiaCore;
import fr.audentia.players.main.AudentiaPlayers;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class AudentiaProtect extends JavaPlugin {

    public static final String VERSION = "0.1.0";

    private static AudentiaProtectManagersProvider managerProvider;

    private CommandsManager commandsManager;
    private ListenersManager listenersManager;
    private TasksManager tasksManager;

    private void loadPlugin() {

        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        AudentiaPlayers audentiaPlayers = (AudentiaPlayers) Bukkit.getServer().getPluginManager().getPlugin("Audentia-Players");
        AudentiaCore audentiaCore = (AudentiaCore) Bukkit.getServer().getPluginManager().getPlugin("Audentia-Core");
        managerProvider = new AudentiaProtectManagersProvider(audentiaPlayers.getManagersProvider(), audentiaCore.getManagersProvider(), this.getDataFolder().getPath());

        this.commandsManager = new CommandsManager(this, VERSION, managerProvider);
        this.listenersManager = new ListenersManager(this, managerProvider);
        this.tasksManager = new TasksManager(this, managerProvider);
    }

    @Override
    public void onEnable() {

        Logger.getLogger("Minecraft").info("Le plugin Audentia Protect s'allume.");

        loadPlugin();

        this.commandsManager.registerCommands();
        this.listenersManager.registerListeners();
        this.tasksManager.startTasks();

    }

    @Override
    public void onDisable() {

        Logger.getLogger("Minecraft").info("Le plugin Audentia Protect s'allume.");

    }

}
