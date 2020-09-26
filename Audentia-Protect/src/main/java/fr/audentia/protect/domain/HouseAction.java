package fr.audentia.protect.domain;

import fr.audentia.core.domain.balance.BalanceManage;
import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.domain.teams.TeamsManager;
import fr.audentia.protect.domain.model.House;
import fr.audentia.protect.domain.model.Location;

import java.util.UUID;

public class HouseAction {

    private final HouseRepository houseRepository;
    private final RolesRepository rolesRepository;
    private final TeamsManager teamsManager;
    private final BalanceManage balanceManage;

    public HouseAction(HouseRepository houseRepository, RolesRepository rolesRepository, TeamsManager teamsManager, BalanceManage balanceManage) {
        this.houseRepository = houseRepository;
        this.rolesRepository = rolesRepository;
        this.teamsManager = teamsManager;
        this.balanceManage = balanceManage;
    }

    public boolean breakSign(Location location) {

        return houseRepository.isRegisteredSign(location);
    }

    public boolean canInteract(UUID playerUUIO, Location location) {

        Role role = rolesRepository.getRole(playerUUIO);

        if (role.number < 4) {
            return true;
        }

        Team team = teamsManager.getTeamOfPlayer(playerUUIO);
        House house = houseRepository.getHouse(team.houseId);

        if (house == null) {
            return false;
        }

        boolean betweenX = Math.min(house.block1Location.x, house.block2Location.x) <= location.x && location.x <= Math.max(house.block1Location.x, house.block2Location.x);
        boolean betweenY = Math.min(house.block1Location.y, house.block2Location.y) <= location.y && location.y <= Math.max(house.block1Location.y, house.block2Location.y);
        boolean betweenZ = Math.min(house.block1Location.z, house.block2Location.z) <= location.z && location.z <= Math.max(house.block1Location.z, house.block2Location.z);

        return betweenX && betweenY && betweenZ;
    }

    public String buyHouse(Location location, UUID playerUUID) {

        if (!houseRepository.isRegisteredSign(location)) {
            return "";
        }

        if (houseRepository.isBoughtBySign(location)) {
            return "";
        }

        int balance = Integer.parseInt(balanceManage.getBalance(playerUUID));
        House house = houseRepository.getHouse(location);
        Role role = rolesRepository.getRole(playerUUID);

        if (role.staff) {
            return "<error>Vous ne pouvez pas acheter une maison.";
        }

        if (house.level < role.number) {
            return "<error>Cette maison est réservée à un grade plus grand que le votre.";
        }

        if (house.price > balance) {
            return "<error>Vous n'avez pas assez d'argent pour acheter cette maison.";
        }

        teamsManager.setHouse(playerUUID, house.id);
        balanceManage.removeFromBalance(playerUUID, house.price);
        return "<success>Maison achetée.";
    }

}
