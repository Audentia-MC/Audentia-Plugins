package fr.audentia.players.infrastructure.teams;

import fr.audentia.players.domain.model.Day;
import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.model.teams.DayTransfers;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.TeamsRepository;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.utils.ColorsUtils;
import org.jooq.Record5;
import org.jooq.Result;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
        Result<Record5<Object, Object, Object, Object, Object>> result = databaseConnection.getDatabaseContext(connection)
                .select(field(name("color")),
                        field(name("balance")),
                        field(name("name")),
                        field(name("day")),
                        field(name("amount")))
                .from(table(name("player"))
                        .join(table(name("team")))
                        .on(field(name("team_id")).eq(field(name("id"))))
                        .leftJoin(table(name("transferts")))
                        .on(field(name("id")).eq(field(name("transferts_team_id")))))
                .where(field(name("uuid")).eq(playerUUID.toString()))
                .fetch();

        Color color = null;
        String name = null;
        Balance balance = null;
        Map<Day, DayTransfers> transfers = new HashMap<>();

        for (Record5<Object, Object, Object, Object, Object> record : result) {

            color = ColorsUtils.fromHexadecimalToColor(record.get(field(name("color")), String.class));
            name = record.get(field(name("name")), String.class);
            balance = new Balance(record.get(field(name("balance")), Integer.class));

            Integer day = record.get(field(name("day")), Integer.class);
            if (day != null) {
                transfers.put(new Day(day), new DayTransfers(record.get(field(name("amount")), Integer.class)));
            }

        }

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return color == null ? Optional.empty() : Optional.of(new Team(color, balance, transfers, name));
    }

}
