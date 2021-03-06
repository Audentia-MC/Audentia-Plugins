package fr.audentia.core.application.commands;

import fr.audentia.core.domain.home.HomesProvide;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHomes implements CommandExecutor {

    private final HomesProvide homesProvide;

    public CommandHomes(HomesProvide homesProvide) {
        this.homesProvide = homesProvide;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        String result = homesProvide.getHomes(player.getUniqueId());
        player.sendMessage(ChatUtils.formatWithPrefix(result));
        return true;
    }

}
