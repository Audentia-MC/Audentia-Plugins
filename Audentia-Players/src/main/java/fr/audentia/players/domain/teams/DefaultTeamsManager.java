package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.utils.ColorsUtils;

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
    public Team getTeam(UUID playerUUID) {

        Team defaultTeam = new Team(Color.BLACK, new Balance(-1), new HashMap<>(), new HashMap<>(), "Default team", -1);

        if (rolesRepository.getRole(playerUUID).isStaff()) {
            defaultTeam = new Team(ColorsUtils.fromHexadecimalToColor("#d99307"), new Balance(-1), new HashMap<>(), new HashMap<>(), "Staff", -1);
        }

        return teamsRepository.get(playerUUID).orElse(defaultTeam);
    }

    @Override
    public void save(Team team) {

        teamsRepository.save(team);
    }

    @Override
    public void setHouse(UUID playerUUID, long id) {

        Team team = getTeam(playerUUID);
        Team newTeam = new Team(team.color, team.balance, team.transfers, team.coliseumKills, team.name, id);

        save(newTeam);
    }

    @Override
    public void resetTeam(UUID playerUUID) {

        Team team = getTeam(playerUUID);
        team = new Team(team.color, new Balance(0), new HashMap<>(), new HashMap<>(), team.name, -1);

        save(team);
    }

    @Override
    public Optional<Team> getTeamByHouseId(long houseId) {

        return teamsRepository.getByHouseId(houseId);
    }

}
