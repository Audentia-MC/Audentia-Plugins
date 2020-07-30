package fr.audentia.core.domain.staff.kick;

import fr.audentia.players.domain.model.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class KickAction {

    private final PlayerKicker playerKicker;
    private final KickRepository kickRepository;
    private final RolesRepository rolesRepository;

    public KickAction(PlayerKicker playerKicker, KickRepository kickRepository, RolesRepository rolesRepository) {
        this.playerKicker = playerKicker;
        this.kickRepository = kickRepository;
        this.rolesRepository = rolesRepository;
    }

    public String kick(UUID staffUUID, UUID kickedUUID) {

        Role role = rolesRepository.getRole(staffUUID);

        if (!role.staff) {
            return "<error>Vous ne pouvez pas kick de joueur.";
        }

        playerKicker.kick(kickedUUID);
        kickRepository.kick(staffUUID, kickedUUID);
        return "<success>Joueur kick.";
    }

}
