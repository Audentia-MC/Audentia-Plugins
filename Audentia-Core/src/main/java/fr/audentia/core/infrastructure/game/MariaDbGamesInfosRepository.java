package fr.audentia.core.infrastructure.game;

import fr.audentia.core.domain.game.GameState;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.bank.EmeraldsLimitation;
import fr.audentia.players.domain.model.Day;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import org.jooq.Record1;

import static org.jooq.impl.DSL.*;

public class MariaDbGamesInfosRepository implements GamesInfosRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbGamesInfosRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Day getDay() {

        Record1<Object> record = databaseConnection.getDatabaseContext()
                .select(field(name("day")))
                .from(table(name("game_infos")))
                .fetchOne();

        return new Day(record.get(field(name("day")), Integer.class));
    }

    @Override
    public EmeraldsLimitation getEmeraldsLimitation(Day day) {

        Record1<Object> record = databaseConnection.getDatabaseContext()
                .select(field(name("limitation")))
                .from(table(name("game_infos"))
                        .join(table("emeralds_limitations"))
                        .on(field(name("day")).eq(field(name("day_number")))))
                .fetchOne();

        return new EmeraldsLimitation(record.get(field(name("limitation")), Integer.class));
    }

    @Override
    public long getStartTimeInSeconds() {

        Record1<Object> record = databaseConnection.getDatabaseContext()
                .select(field(name("start")))
                .from(table(name("game_infos")))
                .fetchOne();

        return record.get(field(name("start")), Long.class);
    }

    @Override
    public long getGameDurationInSeconds() {

        Record1<Object> record = databaseConnection.getDatabaseContext()
                .select(field(name("duration")))
                .from(table(name("game_infos")))
                .fetchOne();

        return record.get(field(name("duration")), Long.class);
    }

    @Override
    public GameState getGameState() {

        Record1<Object> record = databaseConnection.getDatabaseContext()
                .select(field(name("state")))
                .from(table(name("game_infos")))
                .fetchOne();

        return GameState.fromText(record.get(field(name("state")), String.class));
    }

}
