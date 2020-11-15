package fr.audentia.core.domain.staff;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class StaffInventoryOpen {

    private final RolesRepository rolesRepository;
    private final StaffInventoryOpener staffInventoryOpener;
    private final WorldPlayerFinder worldPlayerFinder;

    public StaffInventoryOpen(RolesRepository rolesRepository, StaffInventoryOpener staffInventoryOpener, WorldPlayerFinder worldPlayerFinder) {
        this.rolesRepository = rolesRepository;
        this.staffInventoryOpener = staffInventoryOpener;
        this.worldPlayerFinder = worldPlayerFinder;
    }

    public String openInventory(UUID playerUUID, String targetName) {

        Role role = rolesRepository.getRole(playerUUID);

        if (!worldPlayerFinder.isInWorld(targetName)) {
            return "<error>Ce joueur n'existe pas.";
        }

        if (!role.hasModerationPermission()) {
            return "<error>Vous ne pouvez pas utiliser cette commande.";
        }

        staffInventoryOpener.openInventory(playerUUID, targetName);
        return "<success>Ouverture de l'inventaire de gestion de " + targetName + ".";
    }

}
