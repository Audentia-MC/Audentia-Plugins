package fr.audentia.core.application.commands;

import fr.audentia.core.domain.bank.BankNpcSpawn;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReloadBankNPC implements CommandExecutor {

    private final BankNpcSpawn bankNpcSpawn;

    public CommandReloadBankNPC(BankNpcSpawn bankNpcSpawn) {
        this.bankNpcSpawn = bankNpcSpawn;
    }

    @Override

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        String result = bankNpcSpawn.reloadBankNpc();
        sender.sendMessage(ChatUtils.format(result));

        return true;
    }

}
