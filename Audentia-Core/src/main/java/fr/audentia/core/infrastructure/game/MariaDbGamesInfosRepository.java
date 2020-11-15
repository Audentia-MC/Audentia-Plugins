package fr.audentia.core.infrastructure.game;

import fr.audentia.core.domain.game.GameState;
import fr.audentia.core.domain.game.GamesInfosRepository;
import fr.audentia.players.domain.model.Day;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import org.jooq.Record1;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class MariaDbGamesInfosRepository implements GamesInfosRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbGamesInfosRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Day getDay() {

        GameState gameState = getGameState();

        if (gameState != GameState.PLAYING) {
            return new Day(-1);
        }

        Connection connection = databaseConnection.getConnection();
        Record1<Object> record = databaseConnection.getDatabaseContext(connection)
                .select(field("start_date"))
                .from(table("seasons"))
                .where(field("active").eq(true))
                .fetchOne();

        LocalDateTime startDate = record.get(field("start_date", Timestamp.class)).toLocalDateTime();
        int day = (int) ChronoUnit.DAYS.between(startDate, ZonedDateTime.now()) + 1;

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return new Day(day);
    }

    @Override
    public LocalDateTime getStart() {

        GameState gameState = getGameState();

        if (gameState != GameState.PLAYING) {
            return LocalDateTime.MIN;
        }

        Connection connection = databaseConnection.getConnection();
        Record1<Object> record = databaseConnection.getDatabaseContext(connection)
                .select(field("start_date"))
                .from(table("seasons"))
                .where(field("active").eq(true))
                .fetchOne();

        LocalDateTime startDate = record.get(field("start_date", Timestamp.class)).toLocalDateTime();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return startDate;
    }

    @Override
    public LocalDateTime getEnd() {

        Connection connection = databaseConnection.getConnection();
        Record1<Object> record = databaseConnection.getDatabaseContext(connection)
                .select(field("end_date"))
                .from(table("seasons"))
                .where(field("active").eq(true))
                .fetchOne();

        LocalDateTime endDate = record.get(field("end_date", Timestamp.class)).toLocalDateTime();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return endDate;
    }

    @Override
    public GameState getGameState() {

        Connection connection = databaseConnection.getConnection();
        Record1<Object> record = databaseConnection.getDatabaseContext(connection)
                .select(field("state"))
                .from(table("seasons"))
                .where(field("active").eq(true))
                .fetchOne();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return GameState.fromText(record.get(field("state", String.class)));
    }

    @Override
    public void startSeason(LocalDateTime start, LocalDateTime end) {

        Connection connection = databaseConnection.getConnection();

        databaseConnection.getDatabaseContext(connection)
                .update(table("seasons"))
                .set(field("start_date"), start)
                .set(field("end_date"), end)
                .set(field("state"), "playing")
                .where(field("active").eq(true))
                .execute();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void setState(GameState gameState) {

        Connection connection = databaseConnection.getConnection();
        databaseConnection.getDatabaseContext(connection)
                .update(table("seasons"))
                .set(field("state"), gameState.toString())
                .where(field("active").eq(true))
                .execute();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

}
