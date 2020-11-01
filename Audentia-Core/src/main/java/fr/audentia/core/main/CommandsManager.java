package fr.audentia.core.main;

import fr.audentia.core.application.commands.*;
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
    private final AudentiaCoreManagersProvider provider;
    private final JavaPlugin plugin;

    public CommandsManager(JavaPlugin plugin, String version, AudentiaCoreManagersProvider provider) {
        this.plugin = plugin;
        this.version = version;
        this.provider = provider;
        this.commands = loadCommands();
        this.tabCompleters = loadTabCompleters();
    }

    private Map<PluginCommand, CommandExecutor> loadCommands() {

        Map<PluginCommand, CommandExecutor> loadedCommands = new HashMap<>();

        loadedCommands.put(getCommand("about"), new CommandAbout(version));
        loadedCommands.put(getCommand("balance"), new CommandBalance(provider.balanceManage));
        loadedCommands.put(getCommand("home"), new CommandHome(provider.homeManage));
        loadedCommands.put(getCommand("homes"), new CommandHomes(provider.homesProvide));
        loadedCommands.put(getCommand("sethome"), new CommandSetHome(provider.setHomeManage));
        loadedCommands.put(getCommand("bank"), new CommandBank(provider.bankSlotsProvide));
        loadedCommands.put(getCommand("staff"), new CommandStaff(provider.staffInventoryOpen));
        loadedCommands.put(getCommand("event"), new CommandEvent(provider.eventProvider));
        loadedCommands.put(getCommand("reloadAllPNJ"), new CommandReloadAllPNJ(provider.rolesRepository, provider.npcSpawn));
        loadedCommands.put(getCommand("reloadPNJ"), new CommandReloadPNJ(provider.npcSpawn));
        loadedCommands.put(getCommand("reloadBankPNJ"), new CommandReloadBankNPC(provider.bankNpcSpawn));
        loadedCommands.put(getCommand("start"), new CommandStart(provider.gameStarter));
        loadedCommands.put(getCommand("pause"), new CommandPause(provider.gameStateManage));
        loadedCommands.put(getCommand("resume"), new CommandResume(provider.gameStateManage));

        loadedCommands.put(getCommand("ahelp"), new CommandHelp(loadedCommands));

        return loadedCommands;
    }

    private Map<PluginCommand, TabCompleter> loadTabCompleters() {

        Map<PluginCommand, TabCompleter> loadedTabCompleters = new HashMap<>();

        return loadedTabCompleters;
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
