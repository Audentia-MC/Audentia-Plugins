package fr.audentia.core.domain.staff.ban;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class BanAction {

    private final PlayerBanner playerBanner;
    private final LogsRepository logsRepository;
    private final RolesRepository rolesRepository;

    public BanAction(PlayerBanner playerBanner, LogsRepository logsRepository, RolesRepository rolesRepository) {
        this.playerBanner = playerBanner;
        this.logsRepository = logsRepository;
        this.rolesRepository = rolesRepository;
    }

    public String ban(UUID staffUUID, UUID bannedUUID) {

        Role role = rolesRepository.getRole(staffUUID);

        if (!role.hasModerationPermission()) {
            return "<error>Vous ne pouvez pas bannir de joueur.";
        }

        playerBanner.ban(bannedUUID);
        logsRepository.ban(staffUUID, bannedUUID);
        return "<success>Joueur banni.";
    }

}
