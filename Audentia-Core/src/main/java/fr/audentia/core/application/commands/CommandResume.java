package fr.audentia.core.application.commands;

import fr.audentia.core.domain.game.GameStateManage;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandResume implements CommandExecutor {

    private final GameStateManage gameStateManage;

    public CommandResume(GameStateManage gameStateManage) {
        this.gameStateManage = gameStateManage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        String result = gameStateManage.resume(player.getUniqueId());
        player.sendMessage(ChatUtils.formatWithPrefix(result));

        return true;
    }

}
