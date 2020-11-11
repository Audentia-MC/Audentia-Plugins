package fr.audentia.core.domain.home;

import fr.audentia.core.domain.model.home.Home;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;

import java.util.List;
import java.util.Optional;
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

        List<Home> homes = homeRepository.getHomes(playerUUID);
        boolean doesntExist = homes.stream().map(playerHome -> playerHome.name).noneMatch(home.name::equalsIgnoreCase);

        if (homes.size() >= role.getHomeCount() && doesntExist) {
            return "<error>Votre role ne vous permet pas de créer un nouveau home.";
        }

        if (!worldNameFinder.getWorldName(playerUUID).equals("world")) {
            return "<error>Les homes ne sont disponibles que dans le monde normal.";
        }

        homeRepository.saveHome(playerUUID, home);
        return "<success>Nouveau home défini : " + home.name + ".";
    }

    public String deleteHome(UUID playerUUID, String name) {

        Optional<Home> home = homeRepository.getHome(playerUUID, name);

        if (!home.isPresent()) {
            return "<error>Le home \"" + name + "\"n'existe pas.";
        }

        homeRepository.deleteHome(playerUUID, name);
        return "<success>Votre home \"" + name +"\" a bien été supprimé.";
    }

}
