package fr.audentia.core.domain.game;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class GameDayModifier {

    private final GamesInfosRepository gamesInfosRepository;

    public GameDayModifier(GamesInfosRepository gamesInfosRepository) {
        this.gamesInfosRepository = gamesInfosRepository;
    }

    public void addDay() {

        long startTimeInSeconds = gamesInfosRepository.getStartTimeInSeconds();
        LocalDateTime actualDate = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.ofEpochSecond(startTimeInSeconds, 0, ZoneOffset.UTC);

        long period = ChronoUnit.DAYS.between(startDate, actualDate) + 1;

        gamesInfosRepository.setDay(period);
    }

}
