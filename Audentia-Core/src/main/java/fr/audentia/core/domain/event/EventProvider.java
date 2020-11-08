package fr.audentia.core.domain.event;

import fr.audentia.core.domain.bank.TimeProvider;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.scoreboard.EventsRepository;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.time.Duration;
import java.time.LocalDateTime;
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
        LocalDateTime start = gamesInfosRepository.getStart();

        if (start.isBefore(LocalDateTime.now())) {
            return "<error>Aucun event n'est prévu.";
        }

        LocalDateTime actualTime = timeProvider.getActualTime();
        ZonedDateTime nextEventTime = eventsRepository.getNextEvent();

        if (nextEventTime == null) {
            return "<error>Aucun event n'est prévu.";
        }

        Duration nextEvent = Duration.between(actualTime, nextEventTime);
        return "<success>Le prochain event aura lieu dans : <yellow>" + getDuration(nextEvent.toMillis()) + "<success>.";
    }

    private String getDuration(long time) {
        return DurationFormatUtils.formatDuration(time, DURATION_FORMAT, true);
    }

}
