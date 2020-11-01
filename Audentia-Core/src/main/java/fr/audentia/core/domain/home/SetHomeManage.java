package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.Home;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.UUID;

public class SetHomeManage {

    private final HomeRepository homeRepository;
    private final RolesRepository rolesRepository;
    private final WorldNameFinder worldNameFinder;

    public SetHomeManage(HomeRepository homeRepository, RolesRepository rolesRepository, WorldNameFinder worldNameFinder) {
        this.homeRepository = homeRepository;
        this.rolesRepository = rolesRepository;
        this.worldNameFinder = worldNameFinder;
    }

    public String saveHome(UUID playerUUID, Home home) {

        Role role = rolesRepository.getRole(playerUUID);

        if (home.number < 1) {
            return "<error>Ce numéro est indisponible.";
        }

        if (home.number > role.homeCount) {
            return "<error>Votre role ne vous permet pas d'avoir un home n°" + home.number + ".";
        }

        if (!worldNameFinder.getWorldName(playerUUID).equals("world")) {
            return "<error>Les homes ne sont disponibles que dans le monde normal.";
        }

        homeRepository.saveHome(playerUUID, home);
        return "<success>Nouveau home n°" + home.number + " défini : " + home.name + ".";
    }

}
