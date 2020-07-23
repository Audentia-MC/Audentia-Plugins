package fr.audentia.players.domain.teams;

import java.util.UUID;

public interface TeamsManager {

    Team getTeamOfPlayer(UUID playerUUID);

    void saveTeam(Team team);

}
