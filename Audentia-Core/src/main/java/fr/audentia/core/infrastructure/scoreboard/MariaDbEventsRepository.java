package fr.audentia.core.infrastructure.scoreboard;

import fr.audentia.core.domain.model.scoreboard.Event;
import fr.audentia.core.domain.scoreboard.EventsRepository;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import org.jooq.Record1;
import org.jooq.Result;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;

import static org.jooq.impl.DSL.*;

public class MariaDbEventsRepository implements EventsRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbEventsRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Event getNextEvent() { // TODO: try order by date desc

        long actualTime = Instant.now().getEpochSecond();

        Connection connection = databaseConnection.getConnection();
        Result<Record1<Object>> result = databaseConnection.getDatabaseContext(connection)
                .select(field(name("time")))
                .from(table(name("event")))
                .fetch();

        long nextEventTime = -1;

        for (Record1<Object> record : result) {

            Long time = record.get(field(name("time")), Long.class);
            if (actualTime <= time && (time < nextEventTime || nextEventTime == -1)) {
                nextEventTime = time;
            }

        }

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new Event(nextEventTime);
    }

}
