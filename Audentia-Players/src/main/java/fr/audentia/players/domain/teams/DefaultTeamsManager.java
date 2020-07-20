package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.balance.Balance;

import java.awt.*;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class DefaultTeamsManager implements TeamsManager {

    private final RolesRepository rolesRepository;
    private final TeamsRepository teamsRepository;

    public DefaultTeamsManager(RolesRepository rolesRepository, TeamsRepository teamsRepository) {
        this.rolesRepository = rolesRepository;
        this.teamsRepository = teamsRepository;
    }

    @Override
    public Team getTeamOfPlayer(UUID playerUUID) {

        Team defaultTeam = new Team(new Color(0, 0, 0), new Balance(-1), new HashMap<>());
        Optional<Team> optionalTeam = this.teamsRepository.getTeamOfPlayer(playerUUID);

        return optionalTeam.orElse(defaultTeam);
    }

    @Override
    public boolean isStaff(UUID playerUUID) {
        return rolesRepository.getRole(playerUUID).staff;
    } // TODO : change to RoleManage

    @Override
    public boolean isPlayer(UUID playerUUID) {
        return rolesRepository.getRole(playerUUID).player;
    }

    @Override
    public void saveTeam(Team team) {

    }

}
