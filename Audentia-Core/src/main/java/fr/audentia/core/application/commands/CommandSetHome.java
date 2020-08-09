package fr.audentia.core.application.commands;

import fr.audentia.core.domain.home.SetHomeManage;
import fr.audentia.core.domain.model.home.HomeLocation;
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

        int homeNumber = 1;

        if (args.length != 0 && args[0].matches("[0-9]+")) {
            homeNumber = Integer.parseInt(args[0]);
        }

        Location location = player.getLocation();
        HomeLocation homeLocation = new HomeLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        String message = setHomeManage.saveHome(player.getUniqueId(), homeNumber, homeLocation);
        player.sendMessage(ChatUtils.format(message));
        return true;
    }

}