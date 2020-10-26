package fr.audentia.core.application.commands;

import fr.audentia.core.domain.game.GameStarter;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStart implements CommandExecutor {

    private final GameStarter gameStarter;

    public CommandStart(GameStarter gameStarter) {
        this.gameStarter = gameStarter;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        String result = gameStarter.startGame(player.getUniqueId());

        player.sendMessage(ChatUtils.format(result));

        return true;
    }

}
