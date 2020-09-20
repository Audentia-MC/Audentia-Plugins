package fr.audentia.protect.domain;

import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.protect.domain.model.Location;

import java.sql.Connection;
import java.sql.SQLException;

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

}
