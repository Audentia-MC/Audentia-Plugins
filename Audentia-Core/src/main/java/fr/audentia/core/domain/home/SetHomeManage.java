package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.HomeLocation;
import fr.audentia.players.domain.model.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class SetHomeManage {

    private final HomeRepository homeRepository;
    private final RolesRepository rolesRepository;

    public SetHomeManage(HomeRepository homeRepository, RolesRepository rolesRepository) {
        this.homeRepository = homeRepository;
        this.rolesRepository = rolesRepository;
    }

    public String saveHome(UUID playerUUID, HomeLocation homeLocation) {
        return saveHome(playerUUID, 1, homeLocation);
    }

    public String saveHome(UUID playerUUID, int homeNumber, HomeLocation homeLocation) {

        Role role = rolesRepository.getRole(playerUUID);
        String result = "<error>Votre role ne vous permet pas d'avoir un home n°" + homeNumber + ".";

        if (homeNumber <= role.homeCount) {
            result = "<success>Nouveau home n°" + homeNumber + " défini.";
            homeRepository.saveHome(playerUUID, homeNumber, homeLocation);
        }

        return result;
    }

}
