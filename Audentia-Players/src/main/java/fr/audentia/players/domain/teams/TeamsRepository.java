package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.teams.Team;

import java.util.Optional;
import java.util.UUID;

public interface TeamsRepository {

    Optional<Team> get(UUID playerUUID);

    void save(Team team);

    Optional<Team> getByHouseId(long houseId);

}
