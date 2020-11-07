package fr.audentia.core.domain.game;

import fr.audentia.players.domain.model.Day;

public interface GamesInfosRepository {

    Day getDay();

    long getStartTimeInSeconds();

    long getGameDurationInSeconds();

    GameState getGameState();

    void setDay(long day);

    void addEntry(long startInSeconds, long durationInSeconds);

    void setState(GameState gameState);

}
