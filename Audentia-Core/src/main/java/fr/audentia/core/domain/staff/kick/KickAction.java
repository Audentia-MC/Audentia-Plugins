package fr.audentia.core.domain.staff.kick;

import fr.audentia.core.domain.staff.ban.LogsRepository;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class KickAction {

    private final PlayerKicker playerKicker;
    private final RolesRepository rolesRepository;
    private final LogsRepository logsRepository;

    public KickAction(PlayerKicker playerKicker, RolesRepository rolesRepository, LogsRepository logsRepository) {
        this.playerKicker = playerKicker;
        this.rolesRepository = rolesRepository;
        this.logsRepository = logsRepository;
    }

    public String kick(UUID staffUUID, UUID kickedUUID) {

        Role role = rolesRepository.getRole(staffUUID);

        if (!role.hasModerationPermission()) {
            return "<error>Vous ne pouvez pas kick de joueur.";
        }

        playerKicker.kick(kickedUUID);
        logsRepository.kick(staffUUID, kickedUUID);
        return "<success>Joueur kick.";
    }

}
