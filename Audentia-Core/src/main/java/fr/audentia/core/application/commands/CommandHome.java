package fr.audentia.core.application.commands;

import fr.audentia.core.domain.home.HomeManage;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHome implements CommandExecutor {

    private final HomeManage homeManage;

    public CommandHome(HomeManage homeManage) {
        this.homeManage = homeManage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        String message;

        if (args.length == 0) {
            player.sendMessage(ChatUtils.formatWithPrefix("<error>/home <nom>"));
            return true;
        }

        message = homeManage.registerTeleport(player.getUniqueId(), args[0]);
        player.sendMessage(ChatUtils.formatWithPrefix(message));
        return true;
    }

}
