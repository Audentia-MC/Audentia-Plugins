package fr.audentia.core.application.commands;

import fr.audentia.core.domain.npc.NpcSpawn;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReloadAllPNJ implements CommandExecutor {

    private final RolesRepository rolesRepository;
    private final NpcSpawn npcSpawn;

    public CommandReloadAllPNJ(RolesRepository rolesRepository, NpcSpawn npcSpawn) {
        this.rolesRepository = rolesRepository;
        this.npcSpawn = npcSpawn;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        Role role = rolesRepository.getRole(player.getUniqueId());

        if (role.number > 2) {
            player.sendMessage(ChatUtils.format("<error>Vous ne pouvez pas effectuer cette action."));
            return true;
        }

        String result = npcSpawn.reloadAllNpcs();
        player.sendMessage(ChatUtils.format(result));
        return true;
    }

}
