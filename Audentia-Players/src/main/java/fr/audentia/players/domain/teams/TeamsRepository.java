package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.teams.Team;

import java.util.Optional;
import java.util.UUID;

public interface TeamsRepository {

    Optional<Team> getTeamOfPlayer(UUID playerUUID);

    void saveTeam(Team team);

    Optional<Team> getTeamByHouseId(int houseId);

}
