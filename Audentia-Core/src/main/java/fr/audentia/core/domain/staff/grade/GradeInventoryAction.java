package fr.audentia.core.domain.staff.grade;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class GradeInventoryAction {

    private final RolesRepository rolesRepository;
    private final GradeInventoryOpener gradeInventoryOpener;

    public GradeInventoryAction(RolesRepository rolesRepository, GradeInventoryOpener gradeInventoryOpener) {
        this.rolesRepository = rolesRepository;
        this.gradeInventoryOpener = gradeInventoryOpener;
    }

    public String openGradeInventory(UUID staffUUID, UUID targetUUID) {

        Role role = rolesRepository.getRole(staffUUID);

        if (!role.hasModerationPermission()) {
            return "<error>Vous ne pouvez pas gérer le rôle d'une joueur.";
        }

        gradeInventoryOpener.openInventory(staffUUID, targetUUID);
        return "<success>Gestion des rôles.";
    }

}
