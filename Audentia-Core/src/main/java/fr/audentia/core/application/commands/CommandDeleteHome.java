package fr.audentia.core.application.commands;

import fr.audentia.core.domain.home.SetHomeManage;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDeleteHome implements CommandExecutor {

    private final SetHomeManage setHomeManage;

    public CommandDeleteHome(SetHomeManage setHomeManage) {
        this.setHomeManage = setHomeManage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatUtils.formatWithPrefix("<error>/deleteHome <nom>"));
            return true;
        }

        String name = args[0];
        String result = setHomeManage.deleteHome(player.getUniqueId(), name);
        player.sendMessage(ChatUtils.formatWithPrefix(result));
        return false;
    }

}
