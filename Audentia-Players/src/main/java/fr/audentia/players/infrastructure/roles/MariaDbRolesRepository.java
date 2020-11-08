package fr.audentia.players.infrastructure.roles;

import fr.audentia.players.domain.model.roles.Role;
import fr.audentia.players.domain.teams.RolesRepository;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.utils.ColorsUtils;
import org.jooq.Record;

import java.awt.*;
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
        Record record = databaseConnection.getDatabaseContext(connection)
                .select(field("id", Integer.class),
                        field("color", String.class),
                        field("name", String.class),
                        field("echelon", Integer.class))
                .from(table("users")
                        .join(table("roles"))
                        .on("users.role_id = roles.id"))
                .where(field("minecraft_uuid").eq(playerUUID))
                .fetchOne();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        if (record == null) {
            return aRole()
                    .withColor(Color.BLACK)
                    .withName("Étranger")
                    .withEchelon(1)
                    .build();
        }

        return aRole()
                .withColor(ColorsUtils.fromHexadecimalToColor(record.get(field("color", String.class))))
                .withName(record.get(field("name", String.class)))
                .withEchelon(record.get(field("echelon", Integer.class)))
                .build();
    }

    @Override
    public void changeRole(UUID playerUUID, int roleId) {

        Connection connection = databaseConnection.getConnection();
        databaseConnection.getDatabaseContext(connection)
                .update(table("players"))
                .set(field("role_id"), roleId)
                .where(field("minecraft_uuid").eq(playerUUID.toString()))
                .execute();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

}
