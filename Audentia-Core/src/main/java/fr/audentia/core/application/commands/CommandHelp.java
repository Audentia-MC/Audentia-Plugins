package fr.audentia.core.application.commands;

import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.Map;

public class CommandHelp implements CommandExecutor {

    private final Map<PluginCommand, CommandExecutor> loadedCommands;

    public CommandHelp(Map<PluginCommand, CommandExecutor> loadedCommands) {
        this.loadedCommands = loadedCommands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        player.sendMessage(ChatUtils.formatWithPrefix("<success>----- Commandes -----"));

        loadedCommands.keySet().stream()
                .map(Command::getName)
                .map(name -> "<success>" + name)
                .map(ChatUtils::formatWithPrefix)
                .forEach(player::sendMessage);

        player.sendMessage(ChatUtils.formatWithPrefix("<success>---------------------"));

        return false;
    }

}
