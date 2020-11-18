package fr.audentia.core.infrastructure.staff.ban;

import fr.audentia.core.domain.staff.ban.LogsRepository;
import fr.audentia.players.infrastructure.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import static org.jooq.impl.DSL.*;

public class MariaDbLogsRepository implements LogsRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbLogsRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void ban(UUID staffUUID, UUID bannedUUID) {

        action(staffUUID, bannedUUID, "bannissement");
    }

    @Override
    public void kick(UUID staffUUID, UUID kickedUUID) {

        action(staffUUID, kickedUUID, "expulsion");
    }

    private void action(UUID staffUUID, UUID playerUUID, String description) {

        Connection connection = databaseConnection.getConnection();

        long staffID = databaseConnection.getDatabaseContext(connection)
                .select(field("id"))
                .from(table("users"))
                .where(field("minecraft_uuid").eq(staffUUID.toString()))
                .stream()
                .findAny()
                .map(record -> record.get(field("id", Long.class)))
                .orElse(-1L);

        long playerID = databaseConnection.getDatabaseContext(connection)
                .select(field("id"))
                .from(table("users"))
                .where(field("minecraft_uuid").eq(playerUUID.toString()))
                .stream()
                .findAny()
                .map(record -> record.get(field("id", Long.class)))
                .orElse(-1L);

        databaseConnection.getDatabaseContext(connection)
                .insertInto(table(name("logs")))
                .set(field("source_user_id"), staffID)
                .set(field("target_user_id"), playerID)
                .set(field("description"), description)
                .execute();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

}
