package fr.audentia.protect.domain.house;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.core.domain.protect.CityInfosRepository;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.TeamsManager;
import fr.audentia.protect.domain.model.House;
import fr.audentia.protect.domain.model.HouseBloc;
import fr.audentia.protect.domain.model.Location;

import java.util.Optional;
import java.util.UUID;

public class HouseAction {

    private final HouseRepository houseRepository;
    private final RolesRepository rolesRepository;
    private final TeamsManager teamsManager;
    private final BalanceManage balanceManage;
    private final SignUtils signUtils;
    private final CityInfosRepository cityInfosRepository;

    public HouseAction(HouseRepository houseRepository, RolesRepository rolesRepository, TeamsManager teamsManager, BalanceManage balanceManage, SignUtils signUtils, CityInfosRepository cityInfosRepository) {
        this.houseRepository = houseRepository;
        this.rolesRepository = rolesRepository;
        this.teamsManager = teamsManager;
        this.balanceManage = balanceManage;
        this.signUtils = signUtils;
        this.cityInfosRepository = cityInfosRepository;
    }

    public boolean isRegisteredSign(Location location) {

        return houseRepository.isRegisteredSign(location);
    }

    public boolean canInteract(UUID playerUUIO, Location location) {

        Role role = rolesRepository.getRole(playerUUIO);

        if (role.isStaff()) {
            return true;
        }

        fr.audentia.core.domain.model.location.Location cityLocation = cityInfosRepository.getCityLocation();
        Location protectLocation = new Location(cityLocation.x, cityLocation.y, cityLocation.z);
        double radiusSquared = Math.pow(cityInfosRepository.getCityRadius(), 2);
        double distanceSquared = location.distanceSquared2D(protectLocation);

        if (distanceSquared > radiusSquared) {
            return true;
        }

        Team team = teamsManager.getTeam(playerUUIO);
        House house = houseRepository.getHouse(team.houseId);

        if (house == null) {
            return false;
        }

        for (HouseBloc bloc : house.blocs) {
            boolean betweenX = Math.min(bloc.location1.x, bloc.location2.x) <= location.x && location.x <= Math.max(bloc.location1.x, bloc.location2.x);
            boolean betweenY = Math.min(bloc.location1.y, bloc.location2.y) <= location.y && location.y <= Math.max(bloc.location1.y, bloc.location2.y);
            boolean betweenZ = Math.min(bloc.location1.z, bloc.location2.z) <= location.z && location.z <= Math.max(bloc.location1.z, bloc.location2.z);

            if (betweenX && betweenY && betweenZ) {
                return true;
            }
        }

        return false;
    }

    public String buyHouse(Location location, UUID playerUUID) {

        if (!houseRepository.isRegisteredSign(location)) {
            return "<error>Ce panneau n'est relié à aucune maison.";
        }

        Team team = teamsManager.getTeam(playerUUID);

        if (team.houseId != -1) {
            return "<error>Votre équipe possède déjà une maison.";
        }

        if (houseRepository.isBoughtBySign(location)) {
            return "<error>Cette maison est déjà achetée.";
        }

        int balance = Integer.parseInt(balanceManage.getBalance(playerUUID));
        House house = houseRepository.getHouse(location);
        Role role = rolesRepository.getRole(playerUUID);

        if (role.isStaff()) {
            return "<error>Vous ne pouvez pas acheter une maison.";
        }

        if (!role.hasHousePermission(house.level)) {
            return "<error>Cette maison est réservée à un grade plus grand que le votre.";
        }

        if (house.price > balance) {
            return "<error>Vous n'avez pas assez d'argent pour acheter cette maison.";
        }

        teamsManager.setHouse(playerUUID, house.id);
        balanceManage.removeFromBalance(playerUUID, house.price);
        signUtils.reloadSign(house);
        return "<success>Maison achetée.";
    }

    public Optional<Team> getTeamOwner(Location signLocation) {

        if (!houseRepository.isRegisteredSign(signLocation) || !houseRepository.isBoughtBySign(signLocation)) {
            return Optional.empty();
        }

        long houseId = houseRepository.getHouseId(signLocation);
        return teamsManager.getTeamByHouseId(houseId);
    }

}
