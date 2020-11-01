package fr.audentia.core.domain.staff.ban;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class BanAction {

    private final PlayerBanner playerBanner;
    private final BanRepository banRepository;
    private final RolesRepository rolesRepository;

    public BanAction(PlayerBanner playerBanner, BanRepository banRepository, RolesRepository rolesRepository) {
        this.playerBanner = playerBanner;
        this.banRepository = banRepository;
        this.rolesRepository = rolesRepository;
    }

    public String ban(UUID staffUUID, UUID bannedUUID) {

        Role role = rolesRepository.getRole(staffUUID);

        if (role.number > 2) {
            return "<error>Vous ne pouvez pas bannir de joueur.";
        }

        playerBanner.ban(bannedUUID);
        banRepository.ban(staffUUID, bannedUUID);
        return "<success>Joueur banni.";
    }

}
