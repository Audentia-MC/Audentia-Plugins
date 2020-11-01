package fr.audentia.core.application.commands;

import fr.audentia.core.domain.npc.NpcSpawn;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReloadPNJ implements CommandExecutor {

    private final NpcSpawn npcSpawn;

    public CommandReloadPNJ(NpcSpawn npcSpawn) {
        this.npcSpawn = npcSpawn;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(ChatUtils.format("<error>/reloadNpc <name>"));
            return false;
        }

        String result = npcSpawn.reloadNpc(player.getUniqueId(), args[0]);
        player.sendMessage(ChatUtils.format(result));
        return true;
    }

}
