package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.balance.Balance;

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

        Team defaultTeam = new Team(new Color(0, 0, 0), new Balance(-1), new HashMap<>());
        Optional<Team> optionalTeam = this.teamsRepository.getTeamOfPlayer(playerUUID);

        return optionalTeam.orElse(defaultTeam);
    }

    @Override
    public void saveTeam(Team team) {

    }

}
