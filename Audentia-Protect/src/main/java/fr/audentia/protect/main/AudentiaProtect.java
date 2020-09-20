package fr.audentia.protect.main;

import fr.audentia.players.main.AudentiaPlayers;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

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
        managerProvider = new AudentiaProtectManagersProvider(audentiaPlayers.getManagersProvider(), this.getDataFolder().getPath() + File.separator + "configuration.toml");

        this.commandsManager = new CommandsManager(this, VERSION, managerProvider);
        this.listenersManager = new ListenersManager(this, managerProvider);
        this.tasksManager = new TasksManager(this, managerProvider);
    }

    @Override
    public void onEnable() {

        System.out.println("Le plugin Audentia Protect s'allume.");

        loadPlugin();

        this.commandsManager.registerCommands();
        this.listenersManager.registerListeners();
        this.tasksManager.startTasks();

    }

    @Override
    public void onDisable() {

        System.out.println("Le plugin Audentia Protect s'éteint.");

    }

}
