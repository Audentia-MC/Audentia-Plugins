package fr.audentia.players.domain.teams;

import java.util.UUID;

public interface TeamsManager {

    Team getTeamOfPlayer(UUID playerUUID);

    boolean isStaff(UUID playerUUID);

    boolean isPlayer(UUID playerUUID);

    void saveTeam(Team team);

}
