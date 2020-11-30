package fr.audentia.core.application.commands;

import fr.audentia.core.domain.spawn.SpawnManage;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawn implements CommandExecutor {

    private final SpawnManage spawnManage;

    public CommandSpawn(SpawnManage spawnManage) {
        this.spawnManage = spawnManage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        String message = spawnManage.registerSpawnTeleportation(player.getUniqueId());
        player.sendMessage(ChatUtils.formatWithPrefix(message));
        return true;
    }

}
