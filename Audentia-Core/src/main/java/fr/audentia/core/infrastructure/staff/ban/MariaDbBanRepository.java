package fr.audentia.core.infrastructure.staff.ban;

import fr.audentia.core.domain.staff.ban.BanRepository;
import fr.audentia.players.infrastructure.database.DatabaseConnection;

import java.util.UUID;

import static org.jooq.impl.DSL.*;

public class MariaDbBanRepository implements BanRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbBanRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void ban(UUID staffUUID, UUID bannedUUID) {

        databaseConnection.getDatabaseContext()
                .insertInto(table(name("ban")))
                .columns(field(name("player_uuid")), field(name("staff_uuid")))
                .values(bannedUUID.toString(), staffUUID.toString())
                .execute();
    }

}
