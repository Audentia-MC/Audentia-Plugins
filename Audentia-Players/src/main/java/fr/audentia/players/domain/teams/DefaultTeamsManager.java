package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.model.teams.Team;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class DefaultTeamsManager implements TeamsManager {

    private final TeamsRepository teamsRepository;

    public DefaultTeamsManager(TeamsRepository teamsRepository) {
        this.teamsRepository = teamsRepository;
    }

    @Override
    public Team getTeamOfPlayer(UUID playerUUID) {

        Team defaultTeam = new Team(Color.BLACK, new Balance(-1), new HashMap<>(), "Default team", -1);

        return teamsRepository.getTeamOfPlayer(playerUUID).orElse(defaultTeam);
    }

    @Override
    public void saveTeam(Team team) {

        teamsRepository.saveTeam(team);

    }

    @Override
    public void setHouse(UUID playerUUID, int id) {

        Team team = getTeamOfPlayer(playerUUID);
        Team newTeam = new Team(team.color, team.balance, team.transfers, team.name, id);

        saveTeam(newTeam);
    }

    @Override
    public void resetTeam(UUID playerUUID) {

        Team team = getTeamOfPlayer(playerUUID);
        team = new Team(team.color, new Balance(0), new HashMap<>(), team.name, -1);

        saveTeam(team);
    }

}
