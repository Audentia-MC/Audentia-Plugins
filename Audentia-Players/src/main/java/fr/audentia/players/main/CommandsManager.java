package fr.audentia.players.main;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CommandsManager {

    private final Map<PluginCommand, CommandExecutor> commands;
    private final Map<PluginCommand, TabCompleter> tabCompleters;
    private final String version;
    private final AudentiaPlayersManagersProvider provider;
    private final JavaPlugin plugin;

    public CommandsManager(JavaPlugin plugin, String version, AudentiaPlayersManagersProvider provider) {
        this.plugin = plugin;
        this.version = version;
        this.provider = provider;
        this.commands = loadCommands();
        this.tabCompleters = loadTabCompleters();
    }

    private Map<PluginCommand, CommandExecutor> loadCommands() {

        Map<PluginCommand, CommandExecutor> commands = new HashMap<>();

        return commands;
    }

    private Map<PluginCommand, TabCompleter> loadTabCompleters() {

        Map<PluginCommand, TabCompleter> tabCompleters = new HashMap<>();

        return tabCompleters;
    }

    private PluginCommand getCommand(String name) {
        return this.plugin.getCommand(name);
    }

    public void registerCommands() {

        this.commands.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .forEach(entry -> entry.getKey().setExecutor(entry.getValue()));

        this.tabCompleters.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .forEach(entry -> entry.getKey().setTabCompleter(entry.getValue()));
    }

}
