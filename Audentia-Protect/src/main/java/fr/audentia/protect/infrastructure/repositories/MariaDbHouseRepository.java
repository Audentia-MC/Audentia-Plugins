package fr.audentia.protect.infrastructure.repositories;

import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.protect.domain.house.HouseRepository;
import fr.audentia.protect.domain.model.House;
import fr.audentia.protect.domain.model.HouseBloc;
import fr.audentia.protect.domain.model.Location;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                .where(field(name("sign_x")).eq(location.x)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z)))
                .or(field(name("sign_x")).eq(location.x - 1)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z)))
                .or(field(name("sign_x")).eq(location.x + 1)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z)))
                .or(field(name("sign_x")).eq(location.x)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z - 1)))
                .or(field(name("sign_x")).eq(location.x)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z + 1)))
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
        Result<Record> record = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table(name("house"))
                        .leftJoin(table(name("house_bloc")))
                        .on(field(name("house_id")).eq(field(name("bloc_house_id")))))
                .where(field(name("house_id")).eq(houseId))
                .fetch();

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
        Result<Record> record = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table(name("house")).leftJoin(table(name("house_bloc")))
                        .on(field(name("house_id")).eq(field(name("bloc_house_id")))))
                .where(field(name("sign_x")).eq(location.x)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z)))
                .or(field(name("sign_x")).eq(location.x - 1)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z)))
                .or(field(name("sign_x")).eq(location.x + 1)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z)))
                .or(field(name("sign_x")).eq(location.x)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z - 1)))
                .or(field(name("sign_x")).eq(location.x)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z + 1)))
                .fetch();

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
                .selectFrom(table(name("house")).leftJoin(table(name("house_bloc")))
                        .on(field(name("house_id")).eq(field(name("bloc_house_id")))))
                .fetch();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<House> houses = new ArrayList<>();

        int id = -1;
        int price = -1;
        int level = -1;
        List<HouseBloc> blocs = new ArrayList<>();
        int signX = -1;
        int signY = -1;
        int signZ = -1;
        char blockFace = ' ';

        for (Record record : result) {

            int tempId = record.get(field(name("house_id")), Integer.class);

            if (id != -1 && tempId != id) {
                houses.add(new House(id, price, level, blocs, new Location(signX, signY, signZ), blockFace));
                blocs = new ArrayList<>();
            }

            id = tempId;
            price = record.get(field(name("price")), Integer.class);
            level = record.get(field(name("level")), Integer.class);

            int x0 = record.get(field(name("x0")), Integer.class);
            int y0 = record.get(field(name("y0")), Integer.class);
            int z0 = record.get(field(name("z0")), Integer.class);
            int x1 = record.get(field(name("x1")), Integer.class);
            int y1 = record.get(field(name("y1")), Integer.class);
            int z1 = record.get(field(name("z1")), Integer.class);

            Location location1 = new Location(x0, y0, z0);
            Location location2 = new Location(x1, y1, z1);
            blocs.add(new HouseBloc(location1, location2));

            signX = record.get(field(name("sign_x")), Integer.class);
            signY = record.get(field(name("sign_y")), Integer.class);
            signZ = record.get(field(name("sign_z")), Integer.class);

            blockFace = record.get(field(name("sign_face")), Character.class);

        }

        if (id != -1) {
            houses.add(new House(id, price, level, blocs, new Location(signX, signY, signZ), blockFace));
        }

        return houses;
    }

    @Override
    public int getHouseId(Location location) {

        Connection connection = databaseConnection.getConnection();
        Record1<Object> record = databaseConnection.getDatabaseContext(connection)
                .select(field(name("house_id")))
                .from(table(name("house")))
                .where(field(name("sign_x")).eq(location.x)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z)))
                .or(field(name("sign_x")).eq(location.x - 1)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z)))
                .or(field(name("sign_x")).eq(location.x + 1)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z)))
                .or(field(name("sign_x")).eq(location.x)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z - 1)))
                .or(field(name("sign_x")).eq(location.x)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z + 1)))
                .fetchOne();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return record.get(field(name("house_id")), Integer.class);
    }

    @Override
    public void registerNewHouse(House house) {

        Connection connection = databaseConnection.getConnection();
        databaseConnection.getDatabaseContext(connection)
                .insertInto(table(name("house")))
                .set(field(name("price")), house.price)
                .set(field(name("level")), house.level)
                .set(field(name("sign_face")), String.valueOf(house.signFace))
                .set(field(name("sign_x")), house.signLocation.x)
                .set(field(name("sign_y")), house.signLocation.y)
                .set(field(name("sign_z")), house.signLocation.z)
                .execute();

        for (HouseBloc bloc : house.blocs) {

            databaseConnection.getDatabaseContext(connection)
                    .insertInto(table(name("house_bloc")))
                    .set(field(name("x0")), bloc.location1.x)
                    .set(field(name("y0")), bloc.location1.y)
                    .set(field(name("z0")), bloc.location1.z)
                    .set(field(name("x1")), bloc.location2.x)
                    .set(field(name("y1")), bloc.location2.y)
                    .set(field(name("z1")), bloc.location2.z)
                    .execute();

        }

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private House getHouse(Result<Record> result) {

        if (result == null) {
            return null;
        }

        int id = -1;
        int price = -1;
        int level = -1;
        List<HouseBloc> blocs = new ArrayList<>();
        int signX = -1;
        int signY = -1;
        int signZ = -1;
        char blockFace = ' ';

        for (Record record : result) {

            int tempId = record.get(field(name("house_id")), Integer.class);

            if (id != -1 && tempId != id) {
                blocs = new ArrayList<>();
            }

            id = tempId;
            price = record.get(field(name("price")), Integer.class);
            level = record.get(field(name("level")), Integer.class);

            int x0 = record.get(field(name("x0")), Integer.class);
            int y0 = record.get(field(name("y0")), Integer.class);
            int z0 = record.get(field(name("z0")), Integer.class);
            int x1 = record.get(field(name("x1")), Integer.class);
            int y1 = record.get(field(name("y1")), Integer.class);
            int z1 = record.get(field(name("z1")), Integer.class);

            Location location1 = new Location(x0, y0, z0);
            Location location2 = new Location(x1, y1, z1);
            blocs.add(new HouseBloc(location1, location2));

            signX = record.get(field(name("sign_x")), Integer.class);
            signY = record.get(field(name("sign_y")), Integer.class);
            signZ = record.get(field(name("sign_z")), Integer.class);

            blockFace = record.get(field(name("sign_face")), Character.class);
        }

        if (id == -1) {
            return null;
        }

        return new House(id, price, level, blocs, new Location(signX, signY, signZ), blockFace);
    }

    @Override // TODO: to check
    public boolean isBoughtBySign(Location location) {

        Connection connection = databaseConnection.getConnection();

        boolean present = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table(name("house"))
                        .join(table(name("team")))
                        .on(field(name("house_id")).eq(field(name("team_house_id")))))
                .where(field(name("sign_x")).eq(location.x)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z)))
                .or(field(name("sign_x")).eq(location.x - 1)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z)))
                .or(field(name("sign_x")).eq(location.x + 1)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z)))
                .or(field(name("sign_x")).eq(location.x)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z - 1)))
                .or(field(name("sign_x")).eq(location.x)
                        .and(field(name("sign_y")).eq(location.y))
                        .and(field(name("sign_z")).eq(location.z + 1)))
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
