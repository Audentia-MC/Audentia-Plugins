package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.HomeLocation;
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

    public String saveHome(UUID playerUUID, HomeLocation homeLocation) {
        return saveHome(playerUUID, 1, homeLocation);
    }

    public String saveHome(UUID playerUUID, int homeNumber, HomeLocation homeLocation) {

        Role role = rolesRepository.getRole(playerUUID);

        if (homeNumber < 1) {
            return "<error>Ce numéro est indisponible.";
        }

        if (homeNumber > role.homeCount) {
            return "<error>Votre role ne vous permet pas d'avoir un home n°" + homeNumber + ".";
        }

        if (!worldNameFinder.getWorldName(playerUUID).equals("world")) {
            return "<error>Les homes ne sont disponibles que dans le monde normal.";
        }

        homeRepository.saveHome(playerUUID, homeNumber, homeLocation);
        return "<success>Nouveau home n°" + homeNumber + " défini.";
    }

}
