package fr.audentia.players.infrastructure.teams;

import fr.audentia.players.domain.model.Day;
import fr.audentia.players.domain.model.balance.Balance;
import fr.audentia.players.domain.model.teams.DayTransfers;
import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.domain.teams.TeamsRepository;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import fr.audentia.players.utils.ColorsUtils;
import org.jooq.Record8;
import org.jooq.Result;

import java.awt.*;
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

        Result<Record8<Object, Object, Object, Object, Object, Object, Object, Object>> result = databaseConnection.getDatabaseContext()
                .select(field(name("color")),
                        field(name("balance")),
                        field(name("number")),
                        field(name("home_count")),
                        field(name("staff")),
                        field(name("player")),
                        field(name("day")),
                        field(name("amount")))
                .from(table(name("player"))
                        .join(table(name("team")))
                        .on(field(name("team_id")).eq(field(name("id"))))
                        .join(table(name("transferts")))
                        .on(field(name("id")).eq(field(name("transferts_team_id")))))
                .where(field(name("uuid")).eq(playerUUID.toString()))
                .fetch();

        Color color = null;
        String name = null;
        Balance balance = null;
        Map<Day, DayTransfers> transfers = new HashMap<>();

        for (Record8<Object, Object, Object, Object, Object, Object, Object, Object> record : result) {

            color = ColorsUtils.fromHexadecimalToColor(record.get(field(name("color")), String.class));
            name = record.get(field(name("name")), String.class);
            balance = new Balance(record.get(field(name("balance")), Integer.class));
            transfers.put(new Day(record.get(field(name("day")), Integer.class)),
                    new DayTransfers(record.get(field(name("amount")), Integer.class)));

        }

        return color == null ? Optional.empty() : Optional.of(new Team(color, balance, transfers, name));
    }

}
