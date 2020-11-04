package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.teams.Team;

import java.util.Optional;
import java.util.UUID;

public interface TeamsManager {

    Team getTeam(UUID playerUUID);

    void saveTeam(Team team);

    void setHouse(UUID playerUUID, int id);

    void resetTeam(UUID playerUUID);

    Optional<Team> getTeamByHouseId(int houseId);

}
