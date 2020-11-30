package fr.audentia.core.main;

import fr.audentia.players.main.AudentiaPlayers;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.logging.Logger;

public class AudentiaCore extends JavaPlugin {

    public static final String VERSION = "0.1.0";

    private AudentiaCoreManagersProvider managersProvider;

    private CommandsManager commandsManager;
    private ListenersManager listenersManager;
    private TasksManager tasksManager;

    private void loadPlugin() {

        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        AudentiaPlayers audentiaPlayers = (AudentiaPlayers) Bukkit.getServer().getPluginManager().getPlugin("Audentia-Players");
        managersProvider = new AudentiaCoreManagersProvider(audentiaPlayers.getManagersProvider(), this.getDataFolder().getPath());

        this.commandsManager = new CommandsManager(this, VERSION, managersProvider);
        this.listenersManager = new ListenersManager(this, managersProvider);
        this.tasksManager = new TasksManager(this, managersProvider);
    }

    @Override
    public void onEnable() {

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));

        Logger.getLogger("Minecraft").info("Le plugin Audentia Core s'allume.");

        loadPlugin();

        this.commandsManager.registerCommands();
        this.listenersManager.registerListeners();
        this.tasksManager.startTasks();

    }

    @Override
    public void onDisable() {

        Logger.getLogger("Minecraft").info("Le plugin Audentia Core s'éteint.");

        this.tasksManager.stopTasks();

    }

    public AudentiaCoreManagersProvider getManagersProvider() {
        return managersProvider;
    }

}
