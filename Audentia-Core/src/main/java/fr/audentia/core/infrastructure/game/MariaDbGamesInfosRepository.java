package fr.audentia.core.infrastructure.game;

import fr.audentia.core.domain.game.GameState;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.core.domain.model.bank.EmeraldsLimitation;
import fr.audentia.players.domain.model.Day;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import org.jooq.Record1;

import java.sql.Connection;
import java.sql.SQLException;

import static org.jooq.impl.DSL.*;

public class MariaDbGamesInfosRepository implements GamesInfosRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbGamesInfosRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Day getDay() {

        Connection connection = databaseConnection.getConnection();
        Record1<Object> record = databaseConnection.getDatabaseContext(connection)
                .select(field(name("day")))
                .from(table(name("game_infos")))
                .fetchOne();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new Day(record.get(field(name("day")), Integer.class));
    }

    @Override
    public EmeraldsLimitation getEmeraldsLimitation(Day day) {

        Connection connection = databaseConnection.getConnection();
        Record1<Object> record = databaseConnection.getDatabaseContext(connection)
                .select(field(name("limitation")))
                .from(table(name("emeralds_limitation"))
                        .join(table("game_infos"))
                        .on(field(name("day_number")).eq(field(name("day")))))
                .fetchOne();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new EmeraldsLimitation(record.get(field(name("limitation")), Integer.class));
    }

    @Override
    public long getStartTimeInSeconds() {

        Connection connection = databaseConnection.getConnection();
        Record1<Object> record = databaseConnection.getDatabaseContext(connection)
                .select(field(name("start")))
                .from(table(name("game_infos")))
                .fetchOne();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return record.get(field(name("start")), Long.class);
    }

    @Override
    public long getGameDurationInSeconds() {

        Connection connection = databaseConnection.getConnection();
        Record1<Object> record = databaseConnection.getDatabaseContext(connection)
                .select(field(name("duration")))
                .from(table(name("game_infos")))
                .fetchOne();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return record.get(field(name("duration")), Long.class);
    }

    @Override
    public GameState getGameState() {

        Connection connection = databaseConnection.getConnection();
        Record1<Object> record = databaseConnection.getDatabaseContext(connection)
                .select(field(name("state")))
                .from(table(name("game_infos")))
                .fetchOne();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return GameState.fromText(record.get(field(name("state")), String.class));
    }

}
