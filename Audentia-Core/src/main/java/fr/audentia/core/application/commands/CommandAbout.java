package fr.audentia.core.application.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandAbout implements CommandExecutor {

    private final String version;

    public CommandAbout(String version) {
        this.version = version;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage(ChatColor.DARK_GREEN + "---  Audentia  ---");
        sender.sendMessage(ChatColor.GREEN + "Plugin : Audentia");
        sender.sendMessage(ChatColor.GREEN + "Développeurs : LukaMrt");
        sender.sendMessage(ChatColor.GREEN + "Version : " + this.version);
        sender.sendMessage(ChatColor.GREEN + "Crédits : ");
        sender.sendMessage(ChatColor.GREEN + "   - " + ChatColor.BLUE + "MinusKube" + ChatColor.GREEN + " (inventaires - SmartInvs)");
        sender.sendMessage(ChatColor.GREEN + "   - " + ChatColor.BLUE + "MrMicky" + ChatColor.GREEN + " (scoreboards - FastBoards)");
        sender.sendMessage(ChatColor.DARK_GREEN + "------------------");

        return true;
    }

}
