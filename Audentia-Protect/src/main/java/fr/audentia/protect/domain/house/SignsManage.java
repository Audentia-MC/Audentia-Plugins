package fr.audentia.protect.domain.house;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class SignsManage {

    private final RolesRepository rolesRepository;
    private final HouseRepository houseRepository;
    private final SignUtils signUtils;

    public SignsManage(RolesRepository rolesRepository, HouseRepository houseRepository, SignUtils signUtils) {
        this.rolesRepository = rolesRepository;
        this.houseRepository = houseRepository;
        this.signUtils = signUtils;
    }

    public String reloadAllSigns(UUID playerUUID) {

        Role role = rolesRepository.getRole(playerUUID);

        if (!role.staff) {
            return "<error>Vous ne pouvez pas exécuter cette commande.";
        }

        houseRepository.getAllHouses().forEach(signUtils::reloadSign);
        return "<success>Tous les panneaux ont bien été rechargés.";
    }

}
