package fr.audentia.protect.application.commands;

import fr.audentia.players.utils.ChatUtils;
import fr.audentia.protect.domain.house.SignsManage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReloadSigns implements CommandExecutor {

    private final SignsManage signsManage;

    public CommandReloadSigns(SignsManage signsManage) {
        this.signsManage = signsManage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        String result = signsManage.reloadAllSigns(player.getUniqueId());
        player.sendMessage(ChatUtils.format(result));

        return false;
    }

}
