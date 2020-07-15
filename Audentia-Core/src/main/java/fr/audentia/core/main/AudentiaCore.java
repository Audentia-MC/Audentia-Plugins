package fr.audentia.core.main;

import fr.audentia.players.main.AudentiaPlayers;
import fr.audentia.players.main.AudentiaPlayersManagersProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class AudentiaCore extends JavaPlugin {

    public static final String VERSION = "0.1.0";

    private static AudentiaCoreManagersProvider MANAGERS_PROVIDER;

    private CommandsManager commandsManager;
    private ListenersManager listenersManager;
    private TasksManager tasksManager;

    private void loadPlugin() {
        AudentiaPlayers audentiaPlayers = (AudentiaPlayers) Bukkit.getServer().getPluginManager().getPlugin("Audentia-Players");
        MANAGERS_PROVIDER = new AudentiaCoreManagersProvider(audentiaPlayers.getManagersProvider());

        this.commandsManager = new CommandsManager(this, VERSION, MANAGERS_PROVIDER);
        this.listenersManager = new ListenersManager(this, MANAGERS_PROVIDER);
        this.tasksManager = new TasksManager(this, MANAGERS_PROVIDER);
    }

    @Override
    public void onEnable() {

        System.out.println("Le plugin Audentia s'allume.");

        loadPlugin();

        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }

        this.commandsManager.registerCommands();
        this.listenersManager.registerListeners();
        this.tasksManager.startTasks();

    }

    @Override
    public void onDisable() {

        System.out.println("Le plugin Audentia s'éteint.");

    }



}
