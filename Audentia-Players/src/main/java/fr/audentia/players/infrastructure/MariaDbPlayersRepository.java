package fr.audentia.players.infrastructure;

import fr.audentia.players.domain.PlayersRepository;
import fr.audentia.players.infrastructure.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

public class MariaDbPlayersRepository implements PlayersRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbPlayersRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void addPlayerIfNotRegistered(UUID playerUUID) {

        Connection connection = databaseConnection.getConnection();

        databaseConnection.getDatabaseContext(connection)
                .insertInto(table(name("player")))
                .set(field(name("uuid")), playerUUID.toString())
                .set(field(name("team_id")), 2)
                .set(field(name("role_id")), 10)
                .onConflictDoNothing()
                .execute();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
