package fr.audentia.core.infrastructure.bank;

import fr.audentia.core.domain.bank.BankSlotsRepository;
import fr.audentia.core.domain.model.bank.BankSlots;
import fr.audentia.core.domain.model.bank.Slot;
import fr.audentia.players.domain.model.Day;
import fr.audentia.players.infrastructure.database.DatabaseConnection;
import org.jooq.Record2;
import org.jooq.Result;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.*;

public class MariaDbBankSlotsRepository implements BankSlotsRepository {

    private final DatabaseConnection databaseConnection;

    public MariaDbBankSlotsRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public BankSlots getBankOpenSlots(Day day) {

        Connection connection = databaseConnection.getConnection();
        Result<Record2<Object, Object>> result = databaseConnection.getDatabaseContext(connection)
                .select(field(name("start")), field(name("end")))
                .from(table(name("bank_slot")))
                .where(field(name("day")).eq(day.day))
                .fetch();

        List<Slot> slots = result.stream()
                .map(this::buildSlot)
                .collect(Collectors.toList());

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new BankSlots(slots);
    }

    private Slot buildSlot(Record2<Object, Object> record) {

        Integer start = record.get(field(name("start")), Integer.class);
        Integer end = record.get(field(name("end")), Integer.class);

        return new Slot(start, end);
    }

}
