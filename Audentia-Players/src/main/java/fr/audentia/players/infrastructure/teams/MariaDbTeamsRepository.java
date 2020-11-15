package fr.audentia.players.infrastructure.teams;

import fr.audentia.players.domain.model.Day;
import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.model.teams.ColiseumKills;
import fr.audentia.players.domain.model.teams.DayTransfers;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.TeamsRepository;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.utils.ColorsUtils;
import org.jooq.*;

import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.jooq.impl.DSL.*;

public class MariaDbTeamsRepository implements TeamsRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbTeamsRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Optional<Team> get(UUID playerUUID) {

        Connection connection = databaseConnection.getConnection();

        Record1<Object> startRecord = databaseConnection.getDatabaseContext(connection)
                .select(field("start_date"))
                .from(table("seasons"))
                .where(field("active").eq(true))
                .fetchOne();

        LocalDateTime startDate = startRecord.get(field("start_date", Timestamp.class)).toLocalDateTime();

        Result<Record7<Object, Object, Object, Object, Object, Object, Object>> result = databaseConnection.getDatabaseContext(connection)
                .select(field("color"),
                        field("balance"),
                        field("teams.name"),
                        field("house_id"),
                        field("bank_transfers.date"),
                        field("amount"),
                        field("coliseum_kills.date"))
                .from(table("teams"))
                        .join(table("seasons"))
                        .on(field("seasons.id").eq(field("enrolled_in")))
                        .join(table("users"))
                        .on(field("users.team_id").eq(field("teams.id")))
                        .leftJoin(table("bank_transfers"))
                        .on(field("bank_transfers.team_id").eq(field("teams.id")))
                        .leftJoin(table("coliseum_kills"))
                        .on(field("coliseum_kills.team_id").eq(field("teams.id")))
                .where(field("minecraft_uuid").eq(playerUUID.toString()))
                .and(field("active").eq(true))
                .fetch();

        Color color = null;
        String name = null;
        Balance balance = null;
        Map<Day, DayTransfers> transfers = new HashMap<>();
        Map<Day, ColiseumKills> coliseumKills = new HashMap<>();
        long houseId = -1;

        for (Record7<Object, Object, Object, Object, Object, Object, Object> record : result) {

            color = ColorsUtils.fromHexadecimalToColor(record.get(field("color", String.class)));
            name = record.get(field("teams.name", String.class));
            balance = new Balance(record.get(field("balance", Integer.class)));
            Long recordHouseId = record.get(field("house_id", Long.class));

            if (recordHouseId != null) {
                houseId = recordHouseId;
            }

            Timestamp timestamp = record.get(field("bank_transfers.date", Timestamp.class));
            if (timestamp != null) {
                LocalDateTime transfertDate = timestamp.toLocalDateTime();
                int day = (int) ChronoUnit.DAYS.between(startDate, transfertDate) + 1;

                Day key = new Day(day);
                if (!transfers.containsKey(key)) {
                    transfers.put(key, new DayTransfers(0));
                }

                int amount = record.get(field("amount", Integer.class));
                transfers.put(key, transfers.get(key).add(amount));
            }

            Timestamp timestamp1 = record.get(field("coliseum_kills.date", Timestamp.class));
            if (timestamp1 != null) {
                LocalDateTime killDate = timestamp1.toLocalDateTime();
                int day = (int) ChronoUnit.DAYS.between(startDate, killDate) + 1;

                Day key = new Day(day);
                if (!coliseumKills.containsKey(key)) {
                    coliseumKills.put(key, new ColiseumKills(0));
                }

                coliseumKills.put(key, coliseumKills.get(key).add());
            }

        }

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return color == null ? Optional.empty() : Optional.of(new Team(color, balance, transfers, coliseumKills, name, houseId));
    }

    @Override
    public void save(Team team) {

        Connection connection = databaseConnection.getConnection();
        databaseConnection.getDatabaseContext(connection)
                .update(table("teams"))
                .set(field("balance"), Integer.parseInt(team.balance.toString()))
                .set(field("house_id"), team.houseId)
                .where(field("color").eq(ColorsUtils.fromColorToHexadecimal(team.color)))
                .execute();

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public Optional<Team> getByHouseId(long houseId) {

        Connection connection = databaseConnection.getConnection();

        Record1<Object> startRecord = databaseConnection.getDatabaseContext(connection)
                .select(field("start_date"))
                .from(table("seasons"))
                .where(field("active").eq(true))
                .fetchOne();

        LocalDateTime startDate = startRecord.get(field("start_date", Timestamp.class)).toLocalDateTime();

        Result<Record6<Object, Object, Object, Object, Object, Object>> result = databaseConnection.getDatabaseContext(connection)
                .select(field("color"),
                        field("balance"),
                        field("teams.name"),
                        field("bank_transfers.date"),
                        field("amount"),
                        field("coliseum_kills.date"))
                .from(table("teams"))
                        .join(table("seasons"))
                        .on(field("seasons.id").eq(field("enrolled_in")))
                        .join(table("users"))
                        .on(field("users.team_id").eq(field("teams.id")))
                        .leftJoin(table("bank_transfers"))
                        .on(field("bank_transfers.team_id").eq(field("teams.id")))
                        .leftJoin(table("coliseum_kills"))
                        .on(field("coliseum_kills.team_id").eq(field("teams.id")))
                .where(field("house_id").eq(houseId))
                .and(field("active").eq(true))
                .fetch();

        Color color = null;
        String name = null;
        Balance balance = null;
        Map<Day, DayTransfers> transfers = new HashMap<>();
        Map<Day, ColiseumKills> coliseumKills = new HashMap<>();

        for (Record6<Object, Object, Object, Object, Object, Object> record : result) {

            color = ColorsUtils.fromHexadecimalToColor(record.get(field("color", String.class)));
            name = record.get(field("teams.name", String.class));
            balance = new Balance(record.get(field("balance", Integer.class)));

            Timestamp timestamp = record.get(field("bank_transfers.date", Timestamp.class));
            if (timestamp != null) {
                LocalDateTime transfertDate = timestamp.toLocalDateTime();
                int day = (int) ChronoUnit.DAYS.between(startDate, transfertDate) + 1;

                Day key = new Day(day);
                if (!transfers.containsKey(key)) {
                    transfers.put(key, new DayTransfers(0));
                }

                int amount = record.get(field("amount", Integer.class));
                transfers.put(key, transfers.get(key).add(amount));
            }

            Timestamp timestamp1 = record.get(field("coliseum_kills.date", Timestamp.class));
            if (timestamp1 != null) {
                LocalDateTime killDate = timestamp1.toLocalDateTime();
                int day = (int) ChronoUnit.DAYS.between(startDate, killDate) + 1;

                Day key = new Day(day);
                if (!coliseumKills.containsKey(key)) {
                    coliseumKills.put(key, new ColiseumKills(0));
                }

                coliseumKills.put(key, coliseumKills.get(key).add());
            }

        }

        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return color == null ? Optional.empty() : Optional.of(new Team(color, balance, transfers, coliseumKills, name, houseId));
    }

}
