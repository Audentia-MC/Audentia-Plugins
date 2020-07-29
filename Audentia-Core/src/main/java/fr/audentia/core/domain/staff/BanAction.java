package fr.audentia.core.domain.staff;

import fr.audentia.players.domain.model.Role;
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

        if (!role.staff) {
            return "<error>Vous ne pouvez pas bannir de joueur.";
        }

        playerBanner.ban(bannedUUID);
        banRepository.ban(staffUUID, bannedUUID);
        return "<success>Joueur banni.";
    }

}
