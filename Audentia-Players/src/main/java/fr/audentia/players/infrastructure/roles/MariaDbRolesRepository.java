package fr.audentia.players.infrastructure.roles;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.utils.ColorsUtils;
import org.jooq.Record6;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static fr.audentia.players.domain.model.roles.RoleBuilder.aRole;
import static org.jooq.impl.DSL.*;

public class MariaDbRolesRepository implements RolesRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbRolesRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Role getRole(UUID playerUUID) {

        Connection connection = databaseConnection.getConnection();
        Record6<Object, Object, Object, Object, Object, Object> record = databaseConnection.getDatabaseContext(connection)
                .select(field(name("name")),
                        field(name("color")),
                        field(name("id")),
                        field(name("home_count")),
                        field(name("staff")),
                        field(name("player")))
                .from(table(name("player"))
                        .join(table(name("role")))
                        .on(field(name("role_id")).eq(field(name("id")))))
                .where(field(name("uuid")).eq(playerUUID.toString()))
                .fetchOne();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return aRole()
                .withName(record.get(field(name("name")), String.class))
                .withColor(ColorsUtils.fromHexadecimalToColor(record.get(field(name("color")), String.class)))
                .withNumber(record.get(field(name("id")), Integer.class))
                .withHomeCount(record.get(field(name("home_count")), Integer.class))
                .isPlayer(record.get(field(name("player")), Boolean.class))
                .isStaff(record.get(field(name("staff")), Boolean.class))
                .build();
    }

    @Override
    public void changeRole(UUID playerUUID, int roleNumber) {

        Connection connection = databaseConnection.getConnection();
        databaseConnection.getDatabaseContext(connection)
                .insertInto(table(name("player")))
                .columns(field(name("uuid")), field(name("team_id")), field(name("role_id")))
                .values(playerUUID.toString(), null, roleNumber)
                .onDuplicateKeyUpdate()
                .set(field(name("role_id")), roleNumber)
                .where(field(name("uuid")).eq(playerUUID.toString()))
                .execute();

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
