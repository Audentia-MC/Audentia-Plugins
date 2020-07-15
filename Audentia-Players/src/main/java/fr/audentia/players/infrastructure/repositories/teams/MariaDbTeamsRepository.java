package fr.audentia.players.infrastructure.repositories.teams;

import fr.audentia.players.domain.teams.Team;
import fr.audentia.players.domain.teams.TeamsRepository;

import java.util.Optional;
import java.util.UUID;

public class MariaDbTeamsRepository implements TeamsRepository {

    @Override
    public Optional<Team> getTeamOfPlayer(UUID playerUUID) {
        return Optional.empty();
    }

}
