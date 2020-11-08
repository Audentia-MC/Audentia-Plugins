package fr.audentia.core.domain.staff.inventory;

import fr.audentia.core.domain.staff.WorldPlayerFinder;
import fr.audentia.core.domain.staff.inventory.PlayerInventoryOpener;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class LookInventoryAction {

    private final PlayerInventoryOpener playerInventoryOpener;
    private final RolesRepository rolesRepository;
    private final WorldPlayerFinder worldPlayerFinder;

    public LookInventoryAction(PlayerInventoryOpener playerInventoryOpener, RolesRepository rolesRepository, WorldPlayerFinder worldPlayerFinder) {
        this.playerInventoryOpener = playerInventoryOpener;
        this.rolesRepository = rolesRepository;
        this.worldPlayerFinder = worldPlayerFinder;
    }

    public String lookInventory(UUID staffUUID, UUID targetUUID) {

        if (!rolesRepository.getRole(staffUUID).isStaff()) {
            return "<error>Vous ne pouvez pas ouvrir l'inventaire de quelqu'un.";
        }

        if (!worldPlayerFinder.isInWorld(targetUUID)) {
            return "<error>Ce joueur n'est pas connecté.";
        }

        playerInventoryOpener.openInventory(staffUUID, targetUUID);
        return "<success>Ouverture de l'inventaire.";
    }

}
