package fr.audentia.core.application.commands;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBalance implements CommandExecutor {

    private final BalanceManage balanceManage;

    public CommandBalance(BalanceManage balanceManage) {
        this.balanceManage = balanceManage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        String balance = this.balanceManage.getBalanceWithMessage(player.getUniqueId());
        player.sendMessage(ChatUtils.formatWithPrefix(balance));

        return true;
    }

}
