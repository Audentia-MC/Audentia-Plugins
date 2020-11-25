package fr.audentia.core.application.commands;

import fr.audentia.core.domain.staff.StaffInventoryOpen;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStaff implements CommandExecutor {

    private final StaffInventoryOpen staffInventoryOpen;

    public CommandStaff(StaffInventoryOpen staffInventoryOpen) {
        this.staffInventoryOpen = staffInventoryOpen;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtils.formatWithPrefix("<error>Seuls les joueurs peuvent exécuter cette commande."));
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatUtils.formatWithPrefix("<error>/staff <nom du joueur>."));
            return false;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            player.sendMessage(ChatUtils.formatWithPrefix("<error>Ce joueur n'existe pas."));
            return true;
        }

        String result = staffInventoryOpen.openInventory(player.getUniqueId(), target.getUniqueId(), args[0]);
        player.sendMessage(ChatUtils.formatWithPrefix(result));
        return true;
    }

}
