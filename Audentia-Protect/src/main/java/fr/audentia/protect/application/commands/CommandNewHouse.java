package fr.audentia.protect.application.commands;

import fr.audentia.players.utils.ChatUtils;
import fr.audentia.protect.domain.house.HouseCreation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandNewHouse implements CommandExecutor {

    private final HouseCreation houseCreation;

    public CommandNewHouse(HouseCreation houseCreation) {
        this.houseCreation = houseCreation;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        String result = houseCreation.startCreation(player.getUniqueId());

        player.sendMessage(ChatUtils.formatWithPrefix(result));

        return true;
    }

}
