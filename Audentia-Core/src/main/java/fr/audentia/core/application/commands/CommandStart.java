package fr.audentia.core.application.commands;

import fr.audentia.core.domain.game.GameManage;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStart implements CommandExecutor {

    private final GameManage gameManage;

    public CommandStart(GameManage gameManage) {
        this.gameManage = gameManage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        String days = args.length != 0 ? args[0] : "a";
        String result = gameManage.startGame(player.getUniqueId(), days);

        player.sendMessage(ChatUtils.format(result));

        return true;
    }

}
