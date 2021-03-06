package fr.audentia.players.main;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.logging.Logger;

public class AudentiaPlayers extends JavaPlugin {

    public static final String VERSION = "0.1.0";

    private static AudentiaPlayersManagersProvider MANAGERS_PROVIDER;

    private CommandsManager commandsManager;
    private ListenersManager listenersManager;
    private TasksManager tasksManager;

    private void loadPlugin() {

        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        MANAGERS_PROVIDER = new AudentiaPlayersManagersProvider(this.getDataFolder().getPath());
        this.commandsManager = new CommandsManager(this, VERSION, MANAGERS_PROVIDER);
        this.listenersManager = new ListenersManager(this, MANAGERS_PROVIDER);
        this.tasksManager = new TasksManager(this, MANAGERS_PROVIDER);
    }

    @Override
    public void onEnable() {

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));

        Logger.getLogger("Minecraft").info("Le plugin Audentia Players s'allume.");

        loadPlugin();

        this.commandsManager.registerCommands();
        this.listenersManager.registerListeners();
        this.tasksManager.startTasks();

    }

    @Override
    public void onDisable() {

        Logger.getLogger("Minecraft").info("Le plugin Audentia Players s'allume.");

    }

    public AudentiaPlayersManagersProvider getManagersProvider() {
        return MANAGERS_PROVIDER;
    }

}
