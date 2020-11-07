package fr.audentia.core.infrastructure.bank;

import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.bank.BankSlotsRepository;
import fr.audentia.core.domain.model.bank.BankSlots;
import fr.audentia.core.domain.model.bank.Slot;
import fr.audentia.players.domain.model.Day;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TOMLBankSlotsRepository implements BankSlotsRepository {

    private final String filePath;

    public TOMLBankSlotsRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public BankSlots getBankOpenSlots(Day day) {

        FileConfig fileConfig = FileConfig.of(filePath + File.separator + "configuration.toml");

        fileConfig.load();

        if (fileConfig.isNull(String.valueOf(day.day))) {
            return new BankSlots(new ArrayList<>());
        }

        List<Slot> slots = buildSlots(fileConfig.get(day.day + ".start"), fileConfig.get(day.day + ".end"));

        fileConfig.close();

        return new BankSlots(slots);
    }

    private List<Slot> buildSlots(List<Integer> start, List<Integer> end) {
        List<Slot> slots = new ArrayList<>();

        for (int i = 0; i < Math.min(start.size(), end.size()); i++) {

            slots.add(new Slot(start.get(i), end.get(i)));
        }

        return slots;
    }

}
