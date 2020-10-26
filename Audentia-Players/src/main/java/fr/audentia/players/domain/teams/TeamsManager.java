package fr.audentia.players.domain.teams;

import fr.audentia.players.domain.model.teams.Team;

import java.util.UUID;

public interface TeamsManager {

    Team getTeamOfPlayer(UUID playerUUID);

    void saveTeam(Team team);

    void setHouse(UUID playerUUID, int id);

    void resetTeam(UUID playerUUID);

}
