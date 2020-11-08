package fr.audentia.core.domain.staff.kick;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class KickAction {

    private final PlayerKicker playerKicker;
    private final RolesRepository rolesRepository;

    public KickAction(PlayerKicker playerKicker, RolesRepository rolesRepository) {
        this.playerKicker = playerKicker;
        this.rolesRepository = rolesRepository;
    }

    public String kick(UUID staffUUID, UUID kickedUUID) {

        Role role = rolesRepository.getRole(staffUUID);

        if (role.hasModerationPermission()) {
            return "<error>Vous ne pouvez pas kick de joueur.";
        }

        playerKicker.kick(kickedUUID);
        return "<success>Joueur kick.";
    }

}
