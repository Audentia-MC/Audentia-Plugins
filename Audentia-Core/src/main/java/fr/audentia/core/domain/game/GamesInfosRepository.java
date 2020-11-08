package fr.audentia.core.domain.game;

import fr.audentia.players.domain.model.Day;

import java.time.LocalDateTime;

public interface GamesInfosRepository {

    Day getDay();

    LocalDateTime getStart();

    LocalDateTime getEnd();

    GameState getGameState();

    void startSeason(LocalDateTime startInSeconds, LocalDateTime durationInSeconds);

    void setState(GameState gameState);

}
