package fr.audentia.core.infrastructure.damage;

import fr.audentia.core.domain.damage.ColiseumKillsRepository;
import fr.audentia.players.domain.model.teams.ColiseumKill;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.utils.ColorsUtils;
import org.jooq.Record;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class MariaDbColiseumKillsRepository implements ColiseumKillsRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbColiseumKillsRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void registerKill(Color teamColor, ColiseumKill kill) {

        Connection connection = databaseConnection.getConnection();

        Record teamIdRecord = databaseConnection.getDatabaseContext(connection)
                .select(field("id"))
                .select(field("enrolled_in"))
                .from(table("teams"))
                .join(table("seasons"))
                .on(field("enrolled_in").eq(field("seasons.id")))
                .where(field("color").eq(ColorsUtils.fromColorToHexadecimal(teamColor)))
                .and(field("active").eq(true))
                .fetchOne();

        if (teamIdRecord == null) {
            return;
        }

        long teamId = teamIdRecord.get(field("id", Long.class));
        long seasonId = teamIdRecord.get(field("enrolled_in", Long.class));

        databaseConnection.getDatabaseContext(connection)
                .insertInto(table("coliseum_kills"))
                .set(field("team_id"), teamId)
                .set(field("season_id"), seasonId)
                .set(field("source_user_id"), kill.killer.toString())
                .set(field("target_user_id"), kill.killed.toString())
                .set(field("date"), Timestamp.valueOf(kill.time))
                .execute();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

}
