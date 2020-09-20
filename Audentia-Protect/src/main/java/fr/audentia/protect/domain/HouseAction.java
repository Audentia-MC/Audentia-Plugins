package fr.audentia.protect.domain;

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
    public final TeamsManager teamsManager;

    public HouseAction(HouseRepository houseRepository, RolesRepository rolesRepository, TeamsManager teamsManager) {
        this.houseRepository = houseRepository;
        this.rolesRepository = rolesRepository;
        this.teamsManager = teamsManager;
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

}
