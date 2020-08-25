package fr.audentia.core.domain.event;

import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.scoreboard.Event;
import fr.audentia.core.domain.scoreboard.EventsRepository;
import org.apache.commons.lang.time.DurationFormatUtils;

public class EventProvider {

    public static final String DURATION_FORMAT = "dd:HH:mm:ss";

    private final EventsRepository eventsRepository;
    private final GamesInfosRepository gamesInfosRepository;

    public EventProvider(EventsRepository eventsRepository, GamesInfosRepository gamesInfosRepository) {
        this.eventsRepository = eventsRepository;
        this.gamesInfosRepository = gamesInfosRepository;
    }

    public String getNextEvent() {

        Event nextEvent = eventsRepository.getNextEvent();

        return "<success>Le prochain event aura lieu dans : <yellow>" + getDuration(nextEvent.time) + "<success>.";
    }

    private String getDuration(long time) {

        long startTimeInSeconds = gamesInfosRepository.getStartTimeInSeconds();

        return DurationFormatUtils.formatDuration((time - startTimeInSeconds) * 1_000L, DURATION_FORMAT, true);
    }

}
