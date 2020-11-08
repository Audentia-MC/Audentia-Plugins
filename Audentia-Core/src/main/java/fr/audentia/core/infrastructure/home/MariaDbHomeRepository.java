package fr.audentia.core.infrastructure.home;

import fr.audentia.core.domain.home.HomeRepository;
import fr.audentia.core.domain.model.home.Home;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import org.jooq.Record;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class MariaDbHomeRepository implements HomeRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbHomeRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void saveHome(UUID playerUUID, Home home) {

        Connection connection = databaseConnection.getConnection();

        long count = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table("homes"))
                .where(field("minecraft_uuid").eq(playerUUID.toString()))
                .stream()
                .count();

        databaseConnection.getDatabaseContext(connection)
                .insertInto(table("homes"))
                .set(field("minecraft_uuid"), playerUUID.toString())
                .set(field("name"), home.name)
                .set(field("number"), ++count)
                .set(field("x"), home.x)
                .set(field("y"), home.y)
                .set(field("z"), home.z)
                .onDuplicateKeyUpdate()
                .set(field("name"), home.name)
                .set(field("number"), home.number)
                .set(field("x"), home.x)
                .set(field("y"), home.y)
                .set(field("z"), home.z)
                .execute();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public Optional<Home> getHome(UUID playerUUID, int homeNumber) {

        Connection connection = databaseConnection.getConnection();
        Optional<Home> home = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table("homes"))
                .where(field("minecraft_uuid").eq(playerUUID.toString()))
                .and(field("number").eq(homeNumber))
                .fetch()
                .map(this::buildHome)
                .stream()
                .findFirst();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return home;
    }

    @Override
    public Optional<Home> getHome(UUID playerUUID, String name) {

        Connection connection = databaseConnection.getConnection();
        Optional<Home> home = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table("homes"))
                .where(field("minecraft_uuid").eq(playerUUID.toString()))
                .and(field("name").eq(name))
                .fetch()
                .map(this::buildHome)
                .stream()
                .findFirst();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return home;
    }

    @Override
    public List<Home> getHomes(UUID playerUUID) {

        Connection connection = databaseConnection.getConnection();
        List<Home> homes = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table("homes"))
                .where(field("minecraft_uuid").eq(playerUUID.toString()))
                .orderBy(field("number"))
                .fetch()
                .stream()
                .map(this::buildHome)
                .collect(Collectors.toList());

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return homes;
    }

    private Home buildHome(Record record) {
        return new Home(record.get(field("number", Integer.class)),
                record.get(field("name", String.class)),
                record.get(field("x", Integer.class)),
                record.get(field("y", Integer.class)),
                record.get(field("z", Integer.class)));
    }

}
