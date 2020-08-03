package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.model.teams.Team;

import java.awt.*;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class DefaultTeamsManager implements TeamsManager {

    private final TeamsRepository teamsRepository;

    public DefaultTeamsManager(TeamsRepository teamsRepository) {
        this.teamsRepository = teamsRepository;
    }

    @Override
    public Team getTeamOfPlayer(UUID playerUUID) {

        Team defaultTeam = new Team(Color.BLACK, new Balance(-1), new HashMap<>(), "Default team");

        return teamsRepository.getTeamOfPlayer(playerUUID).orElse(defaultTeam);
    }

    @Override
    public void saveTeam(Team team) {

    }

}
