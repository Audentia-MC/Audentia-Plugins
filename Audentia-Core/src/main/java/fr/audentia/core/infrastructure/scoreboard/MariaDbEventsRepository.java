package fr.audentia.core.infrastructure.scoreboard;

import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.scoreboard.Event;
import fr.audentia.core.domain.scoreboard.EventsRepository;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import org.jooq.Record1;
import org.jooq.Result;

import java.time.Instant;

import static org.jooq.impl.DSL.*;

public class MariaDbEventsRepository implements EventsRepository {

    private final GamesInfosRepository gamesInfosRepository;
    private final DatabaseConnection databaseConnection;

    public MariaDbEventsRepository(GamesInfosRepository gamesInfosRepository, DatabaseConnection databaseConnection) {
        this.gamesInfosRepository = gamesInfosRepository;
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Event getNextEvent() { // TODO: try order by date desc

        long actualTime = Instant.now().getEpochSecond() - gamesInfosRepository.getStartTimeInSeconds();

        Result<Record1<Object>> result = databaseConnection.getDatabaseContext()
                .select(field(name("time")))
                .from(table(name("event")))
                .fetch();

        long nextEventTime = 0;

        for (Record1<Object> record : result) {

            Long time = record.get(field(name("time")), Long.class);
            if (nextEventTime == 0 || (time < nextEventTime && nextEventTime >= actualTime)) {
                nextEventTime = time;
            }

        }

        return new Event(nextEventTime);
    }

}
