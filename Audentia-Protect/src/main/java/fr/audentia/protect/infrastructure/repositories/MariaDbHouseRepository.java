package fr.audentia.protect.infrastructure.repositories;

import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.protect.domain.house.HouseRepository;
import fr.audentia.protect.domain.model.House;
import fr.audentia.protect.domain.model.Location;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.*;

public class MariaDbHouseRepository implements HouseRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbHouseRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public boolean isRegisteredSign(Location location) {

        Connection connection = databaseConnection.getConnection();
        boolean present = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table(name("house")))
                .where(field(name("sign_x")).eq(location.x))
                .and(field(name("sign_y")).eq(location.y))
                .and(field(name("sign_z")).eq(location.z))
                .stream()
                .findAny()
                .isPresent();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return present;
    }

    @Override
    public House getHouse(int houseId) {

        Connection connection = databaseConnection.getConnection();
        Record record = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table(name("house")))
                .where(field(name("id")).eq(houseId))
                .fetchOne();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return getHouse(record);
    }

    @Override
    public House getHouse(Location location) {

        Connection connection = databaseConnection.getConnection();
        Record record = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table(name("house")))
                .where(field(name("sign_x")).eq(location.x))
                .and(field(name("sign_y")).eq(location.y))
                .and(field(name("sign_z")).eq(location.z))
                .fetchOne();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return getHouse(record);
    }

    @Override
    public List<House> getAllHouses() {

        Connection connection = databaseConnection.getConnection();
        Result<Record> result = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table(name("house")))
                .fetch();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result.stream()
                .map(this::getHouse)
                .collect(Collectors.toList());
    }

    @Override
    public int getHouseId(Location location) {

        Connection connection = databaseConnection.getConnection();
        Record1<Object> record = databaseConnection.getDatabaseContext(connection)
                .select(field(name("id")))
                .from(table(name("house")))
                .where(field(name("sign_x")).eq(location.x))
                .and(field(name("sign_y")).eq(location.y))
                .and(field(name("sign_z")).eq(location.z))
                .fetchOne();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return record.get(field(name("id")), Integer.class);
    }

    private House getHouse(Record record) {

        if (record == null) {
            return null;
        }

        int id = record.get(field(name("id")), Integer.class);
        int price = record.get(field(name("price")), Integer.class);
        int level = record.get(field(name("level")), Integer.class);

        int x0 = record.get(field(name("x0")), Integer.class);
        int y0 = record.get(field(name("y0")), Integer.class);
        int z0 = record.get(field(name("z0")), Integer.class);
        int x1 = record.get(field(name("x1")), Integer.class);
        int y1 = record.get(field(name("y1")), Integer.class);
        int z1 = record.get(field(name("z1")), Integer.class);

        int signX = record.get(field(name("sign_x")), Integer.class);
        int signY = record.get(field(name("sign_y")), Integer.class);
        int signZ = record.get(field(name("sign_z")), Integer.class);

        char blockFace = record.get(field(name("sign_face")), Character.class);

        return new House(id, price, level, new Location(x0, y0, z0), new Location(x1, y1, z1), new Location(signX, signY, signZ), blockFace);
    }

    @Override // TODO: to check
    public boolean isBoughtBySign(Location location) {

        Connection connection = databaseConnection.getConnection();

        boolean present = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table(name("house"))
                        .join(table(name("team")))
                        .on(field(name("id")).eq(field(name("house_id")))))
                .where(field(name("sign_x")).eq(location.x))
                .and(field(name("sign_y")).eq(location.y))
                .and(field(name("sign_z")).eq(location.z))
                .stream()
                .findAny()
                .isPresent();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return present;
    }

}
