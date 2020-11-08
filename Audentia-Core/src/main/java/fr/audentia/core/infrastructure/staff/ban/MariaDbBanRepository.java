package fr.audentia.core.infrastructure.staff.ban;

import fr.audentia.core.domain.staff.ban.BanRepository;
import fr.audentia.players.infrastructure.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

public class MariaDbBanRepository implements BanRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbBanRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void ban(UUID staffUUID, UUID bannedUUID) {

        Connection connection = databaseConnection.getConnection();

        int staffID = databaseConnection.getDatabaseContext(connection)
                .select(field("id"))
                .from(table("users"))
                .where(field("minecraft_uuid").eq(staffUUID.toString()))
                .stream()
                .findAny()
                .map(record -> record.get(field("id", Integer.class)))
                .orElse(-1);

        int playerID = databaseConnection.getDatabaseContext(connection)
                .select(field("id"))
                .from(table("users"))
                .where(field("minecraft_uuid").eq(bannedUUID.toString()))
                .stream()
                .findAny()
                .map(record -> record.get(field("id", Integer.class)))
                .orElse(-1);

        databaseConnection.getDatabaseContext(connection)
                .insertInto(table(name("logs")))
                .set(field("source_user_id"), staffID)
                .set(field("target_user_id"), playerID)
                .execute();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

}
