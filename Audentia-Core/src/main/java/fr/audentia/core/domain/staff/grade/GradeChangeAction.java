package fr.audentia.core.domain.staff.grade;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class GradeChangeAction {

    private final RolesRepository rolesRepository;

    public GradeChangeAction(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public String changeGrade(UUID staffUUID, UUID targetUUID, int gradeNumber) {

        Role role = rolesRepository.getRole(staffUUID);

        if (role.number > 2) {
            return "<error>Vous ne pouvez pas gérer le rôle d'une joueur.";
        }

        if (gradeNumber < 6 && role.number > 1) {
            return "<error>Vous ne pouvez pas changer le rôle d'un membre du staff'.";
        }

        rolesRepository.changeRole(targetUUID, gradeNumber);
        return "<success>Le role a bien été changé.";
    }

}
