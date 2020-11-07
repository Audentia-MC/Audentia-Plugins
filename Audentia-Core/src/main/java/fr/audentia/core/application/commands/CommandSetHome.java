package fr.audentia.core.application.commands;

import fr.audentia.core.domain.home.SetHomeManage;
import fr.audentia.core.domain.model.home.Home;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetHome implements CommandExecutor {

    private final SetHomeManage setHomeManage;

    public CommandSetHome(SetHomeManage setHomeManage) {
        this.setHomeManage = setHomeManage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage(ChatUtils.formatWithPrefix("<error>/sethome <numéro> <nom>"));
            return true;
        }

        if (!args[0].matches("[0-9]+")) {
            player.sendMessage(ChatUtils.formatWithPrefix("<error>/sethome <numéro> <nom>"));
            return true;
        }

        int homeNumber = Integer.parseInt(args[0]);

        Location location = player.getLocation();
        Home home = new Home(homeNumber, args[1], location.getBlockX(), location.getBlockY(), location.getBlockZ());

        String message = setHomeManage.saveHome(player.getUniqueId(), home);
        player.sendMessage(ChatUtils.formatWithPrefix(message));
        return true;
    }

}