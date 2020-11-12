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

        if (args.length < 1) {
            player.sendMessage(ChatUtils.formatWithPrefix("<error>/sethome <nom>"));
            return true;
        }

        Location location = player.getLocation();
        Home home = new Home(args[0], location.getBlockX(), location.getBlockY(), location.getBlockZ());

        String message = setHomeManage.saveHome(player.getUniqueId(), home);
        player.sendMessage(ChatUtils.formatWithPrefix(message));
        return true;
    }

}