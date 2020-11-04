package fr.audentia.core.infrastructure.home;

import fr.audentia.core.domain.home.HomeRepository;
import fr.audentia.core.domain.model.home.Home;
import fr.audentia.players.infrastructure.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.*;

public class MariaDbHomeRepository implements HomeRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbHomeRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void saveHome(UUID playerUUID, Home home) {

        Connection connection = databaseConnection.getConnection();
        databaseConnection.getDatabaseContext(connection)
                .insertInto(table(name("home")))
                .columns(field(name("player_uuid")),
                        field(name("home_number")),
                        field(name("x")),
                        field(name("y")),
                        field(name("z")),
                        field(name("name")))
                .values(playerUUID.toString(),
                        home.number,
                        home.x,
                        home.y,
                        home.z,
                        home.name)
                .onDuplicateKeyUpdate()
                .set(field(name("home_number")), home.number)
                .set(field(name("x")), home.x)
                .set(field(name("y")), home.y)
                .set(field(name("z")), home.z)
                .set(field(name("name")), home.name)
                .execute();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Optional<Home> getHome(UUID playerUUID, int homeNumber) {

        Connection connection = databaseConnection.getConnection();
        Optional<Home> home = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table(name("home")))
                .where(field(name("player_uuid")).eq(playerUUID.toString()))
                .and(field(name("home_number")).eq(homeNumber))
                .fetch()
                .map(record -> new Home(
                        record.get(field(name("home_number")), Integer.class),
                        record.get(field(name("name")), String.class),
                        record.get(field(name("x")), Integer.class),
                        record.get(field(name("y")), Integer.class),
                        record.get(field(name("z")), Integer.class)))
                .stream()
                .findFirst();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return home;
    }

    @Override
    public Optional<Home> getHome(UUID playerUUID, String name) {

        Connection connection = databaseConnection.getConnection();
        Optional<Home> home = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table(name("home")))
                .where(field(name("player_uuid")).eq(playerUUID.toString()))
                .and(field(name("name")).eq(name))
                .fetch()
                .map(record -> new Home(
                        record.get(field(name("home_number")), Integer.class),
                        record.get(field(name("name")), String.class),
                        record.get(field(name("x")), Integer.class),
                        record.get(field(name("y")), Integer.class),
                        record.get(field(name("z")), Integer.class)))
                .stream()
                .findFirst();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return home;
    }

    @Override
    public List<Home> getHomes(UUID playerUUID) {

        Connection connection = databaseConnection.getConnection();
        List<Home> homes = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table(name("home")))
                .where(field(name("player_uuid")).eq(playerUUID.toString()))
                .orderBy(field(name("home_number")))
                .fetch()
                .stream()
                .map(record -> new Home(
                        record.get(field(name("home_number")), Integer.class),
                        record.get(field(name("name")), String.class),
                        record.get(field(name("x")), Integer.class),
                        record.get(field(name("y")), Integer.class),
                        record.get(field(name("z")), Integer.class)))
                .collect(Collectors.toList());

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return homes;
    }

}
