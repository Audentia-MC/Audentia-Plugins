package fr.audentia.core.infrastructure.balance;

import fr.audentia.core.domain.balance.TransfersRepository;
import fr.audentia.core.domain.model.balance.BankTransfer;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.utils.ColorsUtils;
import org.jooq.Record;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.jooq.impl.DSL.*;

public class MariaDbTransfersRepository implements TransfersRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbTransfersRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public void registerTransfer(Color teamColor, BankTransfer bankTransfer) {

        Connection connection = databaseConnection.getConnection();

        Record teamIdRecord = databaseConnection.getDatabaseContext(connection)
                .select(field("id"))
                .select(field("enrolled_in"))
                .from(table("teams"))
                .where(field("color").eq(ColorsUtils.fromColorToHexadecimal(teamColor)))
                .fetchOne();

        if (teamIdRecord == null) {
            return;
        }

        int teamId = teamIdRecord.get(field("id", Integer.class));
        int seasonId = teamIdRecord.get(field("enrolled_in", Integer.class));

        databaseConnection.getDatabaseContext(connection)
                .insertInto(table("bank_transfers"))
                .set(field("team_id"), teamId)
                .set(field("season_id"), seasonId)
                .set(field("date"), Timestamp.valueOf(bankTransfer.date))
                .set(field("amount"), bankTransfer.amount)
                .execute();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

}
