package fr.audentia.core.domain.scoreboard;

import fr.audentia.core.domain.model.scoreboard.Scoreboard;

import java.util.UUID;

public interface ScoreboardsRepository {

    void updateScoreboard(UUID playerUUID, Scoreboard scoreboard);

    void removeScoreboard(UUID playerUUID);

}
