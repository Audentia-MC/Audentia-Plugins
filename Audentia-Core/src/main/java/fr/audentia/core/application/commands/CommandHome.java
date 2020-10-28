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

        int homeNumber = 1;

        if (args.length != 0 && args[0].matches("[0-9]+")) {
            homeNumber = Integer.parseInt(args[0]);
        }

        String message = homeManage.registerTeleport(player.getUniqueId(), homeNumber);
        player.sendMessage(ChatUtils.format(message));
        return true;
    }

}
