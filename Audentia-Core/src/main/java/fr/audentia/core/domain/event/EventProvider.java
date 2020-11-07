package fr.audentia.core.domain.event;

import fr.audentia.core.domain.bank.TimeProvider;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.scoreboard.EventsRepository;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.time.ZonedDateTime;

public class EventProvider {

    public static final String DURATION_FORMAT = "ddj HH:mm:ss";

    private final EventsRepository eventsRepository;
    private final GamesInfosRepository gamesInfosRepository;
    private final TimeProvider timeProvider;

    public EventProvider(EventsRepository eventsRepository, GamesInfosRepository gamesInfosRepository, TimeProvider timeProvider) {
        this.eventsRepository = eventsRepository;
        this.gamesInfosRepository = gamesInfosRepository;
        this.timeProvider = timeProvider;
    }

    public String getNextEvent() {
        long startTimeInSeconds = gamesInfosRepository.getStartTimeInSeconds();

        if (startTimeInSeconds == -1) {
            return "<error>Aucun event n'est prévu.";
        }

        long actualTime = timeProvider.getActualTimeInSeconds();
        ZonedDateTime nextEventTime = eventsRepository.getNextEvent();

        return "<success>Le prochain event aura lieu dans : <yellow>" + getDuration(nextEventTime.toEpochSecond() - actualTime) + "<success>.";
    }

    private String getDuration(long time) {
        return DurationFormatUtils.formatDuration(time * 1_000L, DURATION_FORMAT, true);
    }

}
