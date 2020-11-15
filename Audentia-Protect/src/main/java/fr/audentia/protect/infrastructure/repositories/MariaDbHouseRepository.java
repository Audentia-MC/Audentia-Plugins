package fr.audentia.protect.infrastructure.repositories;

import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.protect.domain.house.HouseRepository;
import fr.audentia.protect.domain.model.House;
import fr.audentia.protect.domain.model.HouseBloc;
import fr.audentia.protect.domain.model.Location;
import org.jooq.*;

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
                .selectFrom(table("houses"))
                .where(field("sign_x").eq(location.x)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z)))
                .or(field("sign_x").eq(location.x - 1)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z)))
                .or(field("sign_x").eq(location.x + 1)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z)))
                .or(field("sign_x").eq(location.x)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z - 1)))
                .or(field("sign_x").eq(location.x)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z + 1)))
                .stream()
                .findAny()
                .isPresent();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return present;
    }

    @Override
    public House getHouse(long houseId) {

        Connection connection = databaseConnection.getConnection();
        Result<Record13<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>> record = databaseConnection.getDatabaseContext(connection)
                .select(field("level"),
                        field("price"),
                        field("sign_x"),
                        field("sign_y"),
                        field("sign_z"),
                        field("sign_face"),
                        field("house_id"),
                        field("x1"),
                        field("y1"),
                        field("z1"),
                        field("x2"),
                        field("y2"),
                        field("z2"))
                .from(table("houses"))
                        .leftJoin(table("houses_blocks"))
                        .on(field("houses.id").eq(field("houses_blocks.house_id")))
                        .where(field("houses.id").eq(houseId))
                .fetch();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return buildHouse(record);
    }

    @Override
    public House getHouse(Location location) {

        Connection connection = databaseConnection.getConnection();
        Result<Record13<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>> record = databaseConnection.getDatabaseContext(connection)
                .select(field("level"),
                        field("price"),
                        field("sign_x"),
                        field("sign_y"),
                        field("sign_z"),
                        field("sign_face"),
                        field("house_id"),
                        field("x1"),
                        field("y1"),
                        field("z1"),
                        field("x2"),
                        field("y2"),
                        field("z2"))
                .from(table("houses"))
                .leftJoin(table("houses_blocks"))
                        .on(field("houses.id").eq(field("houses_blocks.house_id")))
                .where(field("sign_x").eq(location.x)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z)))
                .or(field("sign_x").eq(location.x - 1)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z)))
                .or(field("sign_x").eq(location.x + 1)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z)))
                .or(field("sign_x").eq(location.x)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z - 1)))
                .or(field("sign_x").eq(location.x)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z + 1)))
                .fetch();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return buildHouse(record);
    }

    @Override
    public List<House> getAllHouses() {

        Connection connection = databaseConnection.getConnection();
        Result<Record13<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>> result = databaseConnection.getDatabaseContext(connection)
                .select(field("level"),
                        field("price"),
                        field("sign_x"),
                        field("sign_y"),
                        field("sign_z"),
                        field("sign_face"),
                        field("house_id"),
                        field("x1"),
                        field("y1"),
                        field("z1"),
                        field("x2"),
                        field("y2"),
                        field("z2"))
                .from(table("houses"))
                .leftJoin(table("houses_blocks"))
                        .on(field("houses.id").eq(field("houses_blocks.house_id")))
                .fetch();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        if (result.isEmpty()) {
            return new ArrayList<>();
        }

        List<House> houses = new ArrayList<>();

        long id = -1;
        int price = -1;
        int level = -1;
        List<HouseBloc> blocks = new ArrayList<>();
        int signX = -1;
        int signY = -1;
        int signZ = -1;
        char blockFace = ' ';

        for (Record record : result) {

            long tempId = record.get(field(name("house_id"), Long.class));

            if (id != -1 && tempId != id) {
                houses.add(new House(id, price, level, blocks, new Location(signX, signY, signZ), blockFace));
                blocks = new ArrayList<>();
            }

            id = tempId;
            price = record.get(field("price", Integer.class));
            level = record.get(field("level", Integer.class));

            int x0 = record.get(field("x1", Integer.class));
            int y0 = record.get(field("y1", Integer.class));
            int z0 = record.get(field("z1", Integer.class));
            int x1 = record.get(field("x2", Integer.class));
            int y1 = record.get(field("y2", Integer.class));
            int z1 = record.get(field("z2", Integer.class));

            Location location1 = new Location(x0, y0, z0);
            Location location2 = new Location(x1, y1, z1);
            blocks.add(new HouseBloc(location1, location2));

            signX = record.get(field("sign_x", Integer.class));
            signY = record.get(field("sign_y", Integer.class));
            signZ = record.get(field("sign_z", Integer.class));

            blockFace = record.get(field("sign_face", String.class)).charAt(0);
        }

        if (id != -1) {
            houses.add(new House(id, price, level, blocks, new Location(signX, signY, signZ), blockFace));
        }

        return houses;
    }

    @Override
    public long getHouseId(Location location) {

        Connection connection = databaseConnection.getConnection();
        long houseId = databaseConnection.getDatabaseContext(connection)
                .select(field("id"))
                .from(table("houses"))
                .where(field("sign_x").eq(location.x)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z)))
                .or(field("sign_x").eq(location.x - 1)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z)))
                .or(field("sign_x").eq(location.x + 1)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z)))
                .or(field("sign_x").eq(location.x)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z - 1)))
                .or(field("sign_x").eq(location.x)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z + 1)))
                .stream()
                .findAny()
                .map(record -> record.get(field("id", Long.class)))
                .orElse(-1L);


        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return houseId;
    }

    @Override
    public void registerNewHouse(House house) {

        Connection connection = databaseConnection.getConnection();
        databaseConnection.getDatabaseContext(connection)
                .insertInto(table("houses"))
                .set(field("price"), house.price)
                .set(field("level"), house.level)
                .set(field("sign_face"), String.valueOf(house.signFace))
                .set(field("sign_x"), house.signLocation.x)
                .set(field("sign_y"), house.signLocation.y)
                .set(field("sign_z"), house.signLocation.z)
                .execute();

        long houseId = getHouseId(house.signLocation);

        for (HouseBloc block : house.blocs) {

            databaseConnection.getDatabaseContext(connection)
                    .insertInto(table("houses_blocks"))
                    .set(field("house_id"), houseId)
                    .set(field("x1"), block.location1.x)
                    .set(field("y1"), block.location1.y)
                    .set(field("z1"), block.location1.z)
                    .set(field("x2"), block.location2.x)
                    .set(field("y2"), block.location2.y)
                    .set(field("z2"), block.location2.z)
                    .execute();

        }

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    private House buildHouse(Result<Record13<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>> result) {

        if (result == null) {
            return null;
        }

        long id = -1;
        int price = -1;
        int level = -1;
        List<HouseBloc> blocks = new ArrayList<>();
        int signX = -1;
        int signY = -1;
        int signZ = -1;
        char blockFace = ' ';

        for (Record record : result) {

            long tempId = record.get(field(name("house_id"), Long.class));

            if (id != -1 && tempId != id) {
                blocks = new ArrayList<>();
            }

            id = tempId;
            price = record.get(field("price", Integer.class));
            level = record.get(field("level", Integer.class));

            int x0 = record.get(field("x1", Integer.class));
            int y0 = record.get(field("y1", Integer.class));
            int z0 = record.get(field("z1", Integer.class));
            int x1 = record.get(field("x2", Integer.class));
            int y1 = record.get(field("y2", Integer.class));
            int z1 = record.get(field("z2", Integer.class));

            Location location1 = new Location(x0, y0, z0);
            Location location2 = new Location(x1, y1, z1);
            blocks.add(new HouseBloc(location1, location2));

            signX = record.get(field("sign_x", Integer.class));
            signY = record.get(field("sign_y", Integer.class));
            signZ = record.get(field("sign_z", Integer.class));

            blockFace = record.get(field("sign_face", String.class)).charAt(0);
        }

        if (id == -1) {
            return null;
        }

        return new House(id, price, level, blocks, new Location(signX, signY, signZ), blockFace);
    }

    @Override
    public boolean isBoughtBySign(Location location) {

        Connection connection = databaseConnection.getConnection();

        boolean present = databaseConnection.getDatabaseContext(connection)
                .selectFrom(table("houses")
                        .join(table("teams"))
                        .on(field("houses.id").eq(field("teams.house_id"))))
                .where(field("sign_x").eq(location.x)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z)))
                .or(field("sign_x").eq(location.x - 1)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z)))
                .or(field("sign_x").eq(location.x + 1)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z)))
                .or(field("sign_x").eq(location.x)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z - 1)))
                .or(field("sign_x").eq(location.x)
                        .and(field("sign_y").eq(location.y))
                        .and(field("sign_z").eq(location.z + 1)))
                .stream()
                .findAny()
                .isPresent();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return present;
    }

}
