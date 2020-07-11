package fr.audentia.plugin.main;

import org.bukkit.plugin.java.JavaPlugin;

public class AudentiaPlugin extends JavaPlugin {

    public static final String VERSION = "0.1.0";

    private static ManagersProvider MANAGERS_PROVIDER;

    private CommandsManager commandsManager;
    private ListenersManager listenersManager;
    private TasksManager tasksManager;

    private void loadPlugin() {
        MANAGERS_PROVIDER = new ManagersProvider();
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
