package fr.audentia.players.domain.teams;

import java.util.Optional;
import java.util.UUID;

public interface TeamsRepository {

    Optional<Team> getTeamOfPlayer(UUID playerUUID);

}
