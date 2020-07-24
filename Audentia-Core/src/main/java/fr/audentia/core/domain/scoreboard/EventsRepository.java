package fr.audentia.core.domain.scoreboard;

import fr.audentia.core.domain.model.scoreboard.Event;

public interface EventsRepository {

    Event getNextEvent();

}
