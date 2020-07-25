package fr.audentia.core.application.commands;

import fr.audentia.core.domain.bank.BankSlotsProvide;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandBank implements CommandExecutor {

    private final BankSlotsProvide bankSlotsProvide;

    public CommandBank(BankSlotsProvide bankSlotsProvide) {
        this.bankSlotsProvide = bankSlotsProvide;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String bankSlots = bankSlotsProvide.getBankSlots();

        sender.sendMessage(ChatUtils.format(bankSlots));

        return true;
    }

}
