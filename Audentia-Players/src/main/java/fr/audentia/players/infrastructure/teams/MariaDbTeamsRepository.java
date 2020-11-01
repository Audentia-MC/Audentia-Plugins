package fr.audentia.players.infrastructure.teams;

import fr.audentia.players.domain.model.Day;
import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.model.teams.ColiseumKill;
import fr.audentia.players.domain.model.teams.ColiseumKills;
import fr.audentia.players.domain.model.teams.DayTransfers;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.TeamsRepository;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.utils.ColorsUtils;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Result;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.jooq.impl.DSL.*;
import static org.jooq.impl.DSL.name;

public class MariaDbTeamsRepository implements TeamsRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbTeamsRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Optional<Team> getTeamOfPlayer(UUID playerUUID) {

        Connection connection = databaseConnection.getConnection();
        Result<Record10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>> result = databaseConnection.getDatabaseContext(connection)
                .select(field(name("color")),
                        field(name("balance")),
                        field(name("name")),
                        field(name("team_house_id")),
                        field(name("transfert_day")),
                        field(name("amount")),
                        field(name("kill_day")),
                        field(name("killer_uuid")),
                        field(name("killed_uuid")),
                        field(name("time")))
                .from(table(name("player"))
                        .join(table(name("team")))
                        .on(field(name("team_id")).eq(field(name("id"))))
                        .leftJoin(table(name("transferts")))
                        .on(field(name("id")).eq(field(name("transferts_team_id"))))
                        .leftJoin(table(name("coliseum_kills")))
                        .on(field(name("id")).eq(field(name("killer_team_id")))))
                .where(field(name("uuid")).eq(playerUUID.toString()))
                .fetch();

        Color color = null;
        String name = null;
        Balance balance = null;
        Map<Day, DayTransfers> transfers = new HashMap<>();
        Map<Day, ColiseumKills> coliseumKills = new HashMap<>();
        int houseId = -1;

        for (Record10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> record : result) {

            color = ColorsUtils.fromHexadecimalToColor(record.get(field(name("color")), String.class));
            name = record.get(field(name("name")), String.class);
            balance = new Balance(record.get(field(name("balance")), Integer.class));
            Integer recordHouseId = record.get(field(name("team_house_id")), Integer.class);

            if (recordHouseId != null) {
                houseId = recordHouseId;
            }

            Integer transfertDay = record.get(field(name("transfert_day")), Integer.class);
            if (transfertDay != null) {
                transfers.put(new Day(transfertDay), new DayTransfers(record.get(field(name("amount")), Integer.class)));
            }

            Integer killDay = record.get(field(name("kill_day")), Integer.class);
            if (killDay != null) {

                Day day = new Day(killDay);

                if (!coliseumKills.containsKey(day)) {
                    coliseumKills.put(day, new ColiseumKills(new ArrayList<>()));
                }

                ColiseumKill kill = new ColiseumKill(
                        UUID.fromString(record.get(field(name("killer_uuid")), String.class)),
                        UUID.fromString(record.get(field(name("killer_uuid")), String.class)),
                        record.get(field(name("time")), Integer.class));
                coliseumKills.get(day).add(kill);
            }

        }

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return color == null ? Optional.empty() : Optional.of(new Team(color, balance, transfers, coliseumKills, name, houseId));
    }

    @Override
    public void saveTeam(Team team) {

        Connection connection = databaseConnection.getConnection();
        databaseConnection.getDatabaseContext(connection)
                .insertInto(table(name("team")))
                .columns(field(name("color")),
                        field(name("balance")),
                        field(name("name")),
                        field(name("team_house_id")))
                .values(ColorsUtils.fromColorToHexadecimal(team.color), Integer.parseInt(team.balance.toString()), team.name, team.houseId)
                .onConflict(field(name("color")))
                .doUpdate()
                .set(field(name("balance")), Integer.parseInt(team.balance.toString()))
                .set(field(name("team_house_id")), team.houseId)
                .execute();

        Record1<Object> objectRecord1 = databaseConnection.getDatabaseContext(connection)
                .select(field(name("id")))
                .from(table(name("team")))
                .where(field(name("color")).eq(ColorsUtils.fromColorToHexadecimal(team.color)))
                .fetchOne();

        if (objectRecord1 == null) {
            return;
        }

        int id = objectRecord1.get(field(name("id")), Integer.class);

        for (Map.Entry<Day, DayTransfers> entry : team.transfers.entrySet()) {

            databaseConnection.getDatabaseContext(connection)
                    .insertInto(table(name("transferts")))
                    .set(field(name("day")), entry.getKey().day)
                    .set(field(name("amount")), entry.getValue().value)
                    .onDuplicateKeyUpdate()
                    .set(field(name("amount")), entry.getValue())
                    .where(field(name("team_id")).eq(id))
                    .execute();

        }

        for (Map.Entry<Day, ColiseumKills> entry : team.coliseumKills.entrySet()) {

            for (ColiseumKill kill : entry.getValue().kills) {

                databaseConnection.getDatabaseContext(connection)
                        .insertInto(table(name("coliseum_kills")))
                        .set(field(name("kill_day")), entry.getKey().day)
                        .set(field(name("killer_uuid")), kill.killer.toString())
                        .set(field(name("killed_uuid")), kill.killed.toString())
                        .set(field(name("time")), kill.timeInSeconds)
                        .onConflictDoNothing()
                        .execute();

            }

        }

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Optional<Team> getTeamByHouseId(int houseId) {

        Connection connection = databaseConnection.getConnection();
        Result<Record10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>> result = databaseConnection.getDatabaseContext(connection)
                .select(field(name("color")),
                        field(name("balance")),
                        field(name("name")),
                        field(name("team_house_id")),
                        field(name("transfert_day")),
                        field(name("amount")),
                        field(name("kill_day")),
                        field(name("killer_uuid")),
                        field(name("killed_uuid")),
                        field(name("time")))
                .from(table(name("player"))
                        .join(table(name("team")))
                        .on(field(name("team_id")).eq(field(name("id"))))
                        .leftJoin(table(name("transferts")))
                        .on(field(name("id")).eq(field(name("transferts_team_id"))))
                        .leftJoin(table(name("coliseum_kills")))
                        .on(field(name("id")).eq(field(name("killer_team_id")))))
                .where(field(name("team_house_id")).eq(houseId))
                .fetch();

        Color color = null;
        String name = null;
        Balance balance = null;
        Map<Day, DayTransfers> transfers = new HashMap<>();
        Map<Day, ColiseumKills> coliseumKills = new HashMap<>();

        for (Record10<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> record : result) {

            color = ColorsUtils.fromHexadecimalToColor(record.get(field(name("color")), String.class));
            name = record.get(field(name("name")), String.class);
            balance = new Balance(record.get(field(name("balance")), Integer.class));

            Integer transfertDay = record.get(field(name("transfert_day")), Integer.class);
            if (transfertDay != null) {
                transfers.put(new Day(transfertDay), new DayTransfers(record.get(field(name("amount")), Integer.class)));
            }

            Integer killDay = record.get(field(name("kill_day")), Integer.class);
            if (killDay != null) {

                Day day = new Day(killDay);

                if (!coliseumKills.containsKey(day)) {
                    coliseumKills.put(day, new ColiseumKills(new ArrayList<>()));
                }

                ColiseumKill kill = new ColiseumKill(
                        UUID.fromString(record.get(field(name("killer_uuid")), String.class)),
                        UUID.fromString(record.get(field(name("killer_uuid")), String.class)),
                        record.get(field(name("time")), Integer.class));
                coliseumKills.get(day).add(kill);
            }

        }

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return color == null ? Optional.empty() : Optional.of(new Team(color, balance, transfers, coliseumKills, name, houseId));
    }

}
