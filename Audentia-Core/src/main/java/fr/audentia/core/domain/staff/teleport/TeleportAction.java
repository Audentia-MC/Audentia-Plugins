package fr.audentia.core.domain.staff.teleport;

import fr.audentia.core.domain.staff.WorldPlayerFinder;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class TeleportAction {

    private final PlayerTeleporter playerTeleporter;
    private final RolesRepository rolesRepository;
    private final WorldPlayerFinder worldPlayerFinder;

    public TeleportAction(PlayerTeleporter playerTeleporter, RolesRepository rolesRepository, WorldPlayerFinder worldPlayerFinder) {
        this.playerTeleporter = playerTeleporter;
        this.rolesRepository = rolesRepository;
        this.worldPlayerFinder = worldPlayerFinder;
    }

    public String teleport(UUID playerUUID, UUID targetUUID) {

        if (!rolesRepository.getRole(playerUUID).isStaff()) {
            return "<error>Vous ne pouvez pas vous tp à quelqu'un.";
        }

        if (!worldPlayerFinder.isInWorld(targetUUID)) {
            return "<error>Ce joueur n'est pas connecté.";
        }

        playerTeleporter.teleport(playerUUID, targetUUID);
        return "<success>Téléportation réussie.";
    }

}
