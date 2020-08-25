package fr.audentia.core.infrastructure.home;

import fr.audentia.core.domain.home.HomeRepository;
import fr.audentia.core.domain.model.home.HomeLocation;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Result;

import java.util.ArrayList;
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
    public void saveHome(UUID playerUUID, int homeNumber, HomeLocation homeLocation) {

        databaseConnection.getDatabaseContext()
                .insertInto(table(name("home")))
                .columns(field(name("player_uuid")),
                        field(name("home_number")),
                        field(name("x")),
                        field(name("y")),
                        field(name("z")))
                .values(playerUUID.toString(),
                        homeNumber,
                        homeLocation.x,
                        homeLocation.y,
                        homeLocation.z)
                .onDuplicateKeyUpdate()
                .set(field(name("x")), homeLocation.x)
                .set(field(name("y")), homeLocation.y)
                .set(field(name("z")), homeLocation.z)
                .execute();
    }

    @Override
    public Optional<HomeLocation> getHome(UUID playerUUID, int homeNumber) {

        Result<Record> result = databaseConnection.getDatabaseContext()
                .selectFrom(table(name("home")))
                .where(field(name("player_uuid")).eq(playerUUID.toString()))
                .and(field(name("home_number")).eq(homeNumber))
                .fetch();

        for (Record record : result) {

            HomeLocation homeLocation = new HomeLocation(
                    record.get(field(name("x")), Integer.class),
                    record.get(field(name("y")), Integer.class),
                    record.get(field(name("z")), Integer.class));

            return Optional.of(homeLocation);

        }

        return Optional.empty();
    }

    @Override
    public List<HomeLocation> getHomes(UUID playerUUID) {

        return databaseConnection.getDatabaseContext()
                .selectFrom(table(name("home")))
                .where(field(name("player_uuid")).eq(playerUUID.toString()))
                .orderBy(field(name("home_number")))
                .fetch()
                .stream()
                .map(record -> new HomeLocation(
                        record.get(field(name("x")), Integer.class),
                        record.get(field(name("y")), Integer.class),
                        record.get(field(name("z")), Integer.class)))
                .collect(Collectors.toList());
    }

}
