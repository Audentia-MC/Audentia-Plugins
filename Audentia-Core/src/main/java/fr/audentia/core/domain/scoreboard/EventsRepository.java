package fr.audentia.core.domain.scoreboard;

import java.time.ZonedDateTime;

public interface EventsRepository {

    ZonedDateTime getNextEvent();

}
