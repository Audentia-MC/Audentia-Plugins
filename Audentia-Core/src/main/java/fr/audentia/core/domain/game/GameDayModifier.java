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

        if (startTimeInSeconds == -1) {
            return;
        }

        ZonedDateTime actualDate = ZonedDateTime.now();
        ZonedDateTime startDate = ZonedDateTime.ofInstant(Instant.ofEpochSecond(startTimeInSeconds), ZoneId.of("Europe/Paris"));

        long period = ChronoUnit.DAYS.between(startDate, actualDate) + 1;

        gamesInfosRepository.setDay(period);
    }

}
